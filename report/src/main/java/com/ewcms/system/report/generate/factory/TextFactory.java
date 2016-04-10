package com.ewcms.system.report.generate.factory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.system.externalds.entity.BaseDs;
import com.ewcms.system.externalds.generate.factory.DataSourceFactoryable;
import com.ewcms.system.externalds.generate.factory.init.EwcmsDataSourceFactory;
import com.ewcms.system.externalds.generate.service.EwcmsDataSourceServiceable;
import com.ewcms.system.report.entity.Parameter;
import com.ewcms.system.report.entity.TextReport;
import com.ewcms.system.report.entity.TextReport.Type;
import com.ewcms.system.report.exception.ReportException;
import com.ewcms.system.report.generate.entity.PageShowParam;
import com.ewcms.system.report.generate.service.text.BaseTextGenerateServiceable;
import com.ewcms.system.report.generate.service.text.HtmlGenerateService;
import com.ewcms.system.report.generate.service.text.PdfGenerateService;
import com.ewcms.system.report.generate.service.text.RtfGenerateService;
import com.ewcms.system.report.generate.service.text.XlsGenerateService;
import com.ewcms.system.report.generate.service.text.XmlGenerateService;
import com.ewcms.system.report.generate.util.ParamConversionPage;

/**
 * @author 吴智俊
 */
@Service
public class TextFactory {

	private static final Logger logger = LoggerFactory.getLogger(TextFactory.class);
	
    private static Map<String, BaseTextGenerateServiceable> textReportMap;

    static {
        textReportMap = new HashMap<String, BaseTextGenerateServiceable>();

        textReportMap.put("HTML", new HtmlGenerateService());
        textReportMap.put("PDF", new PdfGenerateService());
        textReportMap.put("XLS", new XlsGenerateService());
        textReportMap.put("RTF", new RtfGenerateService());
        textReportMap.put("XML", new XmlGenerateService());
    }
    @Autowired
    private DataSource dataSource;
    @Autowired
    private EwcmsDataSourceFactory ewcmsDataSourceFactory;
    @Autowired
    private ConversionService conversionService;

    public void setAlqcDataSourceFactory(EwcmsDataSourceFactory alqcDataSourceFactory) {
        this.ewcmsDataSourceFactory = alqcDataSourceFactory;
    }

    public EwcmsDataSourceFactory getAlqcDataSourceFactory() {
        return this.ewcmsDataSourceFactory;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SuppressWarnings("unchecked")
	public byte[] export(Map<String, String> parameters, TextReport report, Type type, HttpServletResponse response) {
        EwcmsDataSourceServiceable service = null;
        try {
            if (report == null) {
                return null;
            }
            
            // 根据传入的报表类型,取得相应的报表引擎类
            BaseTextGenerateServiceable textEngineClass = textReportMap.get(type.name());
            if (textEngineClass == null) {
                return null;
            }

            // 把传入的String类型的参数，转换成报表指定类型参数
            Set<Parameter> list = report.getParameters();
            Map<String, Object> textParam = new HashMap<String, Object>();
            if (parameters == null || parameters.size() == 0){
	            for (Parameter param : list) {
	                String value = param.getDefaultValue();
	                if (value == null) {
	                    continue;
	                }
		            String className = param.getClassName();
		            Class<Object> forName = (Class<Object>)Class.forName(className);
		            if (conversionService.canConvert(String.class, forName)){
		            	Object paramValue = conversionService.convert(value, forName);
			            textParam.put(param.getEnName(), paramValue);
		            }
	            }
            }else{
	            for (Parameter param : list) {
	                String value = parameters.get(param.getEnName());
	                if (value == null) {
	                    continue;
	                }
		            String className = param.getClassName();
		            Class<Object> forName = (Class<Object>)Class.forName(className);
		            if (conversionService.canConvert(String.class, forName)){
		            	Object paramValue = conversionService.convert(value, forName);
			            textParam.put(param.getEnName(), paramValue);
		            }
	            }
            }

            // 取得数据源
            BaseDs alqcDataSource = report.getBaseDs();
            if (alqcDataSource == null) {
                // 使用系统数据源
                textParam.put(JRParameter.REPORT_CONNECTION, dataSource.getConnection());
            } else {
                // 使用外部数据
                DataSourceFactoryable factory = (DataSourceFactoryable) getAlqcDataSourceFactory().getBean(alqcDataSource.getClass());
                service = factory.createService(alqcDataSource);
                textParam.put(JRParameter.REPORT_CONNECTION, service.openConnection());
            }

            // 取得报表文件
            byte[] textReportEntity = report.getTextEntity();
            InputStream in = new ByteArrayInputStream(textReportEntity);
            return textEngineClass.export(in, type, textParam, response);
        } catch (ClassNotFoundException e) {
            logger.error("Text Export ClassNotFoundException : ", e);
            throw new ReportException("report.text.not.class", null);
		} catch (SQLException e) {
			logger.error("Text Export SQLException : ", e);
			throw new ReportException("report.text.connection.error", null);
		} catch (Exception e){
			logger.error("Text Export Exception : ", e);
			throw new ReportException("report.text.unknow.error", e.getStackTrace());
		} finally {
            if (service != null) {
                service.closeConnection();
            }
        }
    }

    public List<PageShowParam> textParameters(TextReport report) {
        Assert.notNull(report);
        Set<Parameter> paramSet = report.getParameters();
        return ParamConversionPage.conversion(paramSet);
    }
}
