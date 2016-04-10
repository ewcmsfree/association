package com.ewcms.system.report.generate.service.text;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.system.report.entity.TextReport.Type;
import com.ewcms.system.report.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * 抽象报表引擎
 * 
 * @author 吴智俊
 */
public abstract class BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(BaseTextGenerateServiceable.class);

	public byte[] export(InputStream in, Type type,	Map<String, Object> parameters, HttpServletResponse response) {
		try {
			JasperDesign design = JRXmlLoader.load(in);
			JasperReport jasperReport = JasperCompileManager.compileReport(design);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			return generate(jasperPrint, response);
		} catch (JRException e) {
			logger.error("Base Text Generate Exception", e);
			throw new ReportException("report.text.export.error", null);
		}
	}

	/**
	 * 实现报表类型实例
	 * 
	 * @param out
	 * @param jasperPrint
	 */
	protected abstract byte[] generate(JasperPrint jasperPrint, HttpServletResponse response);
}
