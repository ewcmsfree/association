package com.ewcms.system.report.generate.service.text;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.system.report.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 * excel报表
 * 
 * @author 吴智俊
 */
public class XlsGenerateService extends BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(XlsGenerateService.class);
			
    protected byte[] generate(JasperPrint jasperPrint, HttpServletResponse response) {
        try {
            // 必须生成不然中文乱码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            JRXlsExporter exporter = new JRXlsExporter();

            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            // 删除记录最下面的空行
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            // 删除多余的ColumnHeader
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, new String[]{"AlqcXls"});
            // 显示边框
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);;
            
            exporter.exportReport();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            if (response != null) {
                response.setContentLength(bytes.length);
                response.setContentType("application/vnd.ms-excel");
            }
            return bytes;
        } catch (JRException e) {
        	logger.error("Xls Generate Exception", e);
            throw new ReportException("report.text.xls.error", null);
        }
    }
}
