package com.ewcms.system.report.generate.service.text;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.system.report.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;

/**
 * XML格式报表
 * 
 * @author 吴智俊
 */
public class XmlGenerateService extends BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(XmlGenerateService.class);

    protected byte[] generate(JasperPrint jasperPrint, HttpServletResponse response) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // JasperExportManager.exportReportToXmlStream(jasperPrint, out);
            JRXmlExporter exporter = new JRXmlExporter();
            exporter.setParameter(JRXmlExporterParameter.JASPER_PRINT,
                    jasperPrint);
            exporter.setParameter(JRXmlExporterParameter.OUTPUT_STREAM,
                    byteArrayOutputStream);
            exporter.exportReport();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            if (response != null) {
                response.setContentLength(bytes.length);
                response.setContentType("application/xml");
            }
            return bytes;
        } catch (JRException e) {
            logger.error("Xml Generate Exception", e);
            throw new ReportException("report.text.xml.error", null);
        }
    }
}
