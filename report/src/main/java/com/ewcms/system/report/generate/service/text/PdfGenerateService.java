package com.ewcms.system.report.generate.service.text;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.ewcms.system.report.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

// import org.pdfbox.pdmodel.encryption.PDStandardEncryption;
/**
 * PDF格式报表
 * 
 * @author 吴智俊
 */
public class PdfGenerateService extends BaseTextGenerateServiceable {

	private static final Logger logger = LoggerFactory.getLogger(PdfGenerateService.class);

    protected byte[] generate(JasperPrint jasperPrint, HttpServletResponse response) {

        try {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // PDDocument pdf = PDDocument.load(in);

            // JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
                    jasperPrint);
            exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM,
                    byteArrayOutputStream);
            exporter.exportReport();

            byte[] bytes = byteArrayOutputStream.toByteArray();

            if (response != null) {
                response.setContentLength(bytes.length);
                response.setContentType("application/pdf");
            }

            return bytes;

            // 加密PDF文档
            // pdf.setEncryptionDictionary(pdfEncryption());
            // pdf.encrypt("www.jict.org", null);
        } catch (JRException e) {
            logger.error("Pdf Generate Exception", e);
            throw new ReportException("report.text.pdf.error", null);
        }

    }
    // private PDStandardEncryption pdfEncryption(){
    //
    // PDStandardEncryption encryption = new PDStandardEncryption();
    // // 文档不能插入,旋转,删除页面
    // encryption.setCanAssembleDocument(false);
    // // 文档不能提取
    // encryption.setCanExtractContent(false);
    // // 文档不能拷贝
    // encryption.setCanExtractForAccessibility(false);
    // // 文档不能填充
    // encryption.setCanFillInForm(false);
    // // 文档不能修改
    // encryption.setCanModify(false);
    // // 文档注释不能修改
    // encryption.setCanModifyAnnotations(false);
    // // 文档可以印
    // encryption.setCanPrint(true);
    //
    // return encryption;
    // }
}
