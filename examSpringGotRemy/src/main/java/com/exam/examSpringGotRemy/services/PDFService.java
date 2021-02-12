package com.exam.examSpringGotRemy.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.exam.examSpringGotRemy.model.Annonce;
import com.lowagie.text.DocumentException;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PDFService {

	public static String output = "src/main/resources/static/pdf/Liste_annonces.pdf";

    
    private String parseThymeleafTemplate(Annonce annonce) {
    	ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    	templateResolver.setSuffix(".html");
    	templateResolver.setTemplateMode(TemplateMode.HTML);
    	
    	TemplateEngine templateEngine = new TemplateEngine();
    	templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();

        context.setVariable("annonce", annonce);

        return templateEngine.process("templates/annonces_pdf.html", context);
    }
    
    public void generatePdfFromHtml(Annonce annonce) throws IOException, DocumentException {
        String html = this.parseThymeleafTemplate(annonce);
        output = "src/main/resources/static/pdf/Annonce_"+annonce.getId()+".pdf";
        OutputStream outputStream = new FileOutputStream(output);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

}
