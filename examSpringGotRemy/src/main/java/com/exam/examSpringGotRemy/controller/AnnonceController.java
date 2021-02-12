package com.exam.examSpringGotRemy.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.exam.examSpringGotRemy.model.Annonce;
import com.exam.examSpringGotRemy.services.AnnonceService;
import com.exam.examSpringGotRemy.services.PDFService;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping(path="/")
public class AnnonceController {

	@Autowired
	private AnnonceService annonceService;
	
	@Autowired
	private PDFService pdfService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView annonceList() {
		List<Annonce> annonces = this.annonceService.getAnnonces();
		ModelAndView mv = new ModelAndView("list");
		mv.addObject("annonces",annonces);
		return mv;
	}
	
	@RequestMapping(value="/detail/{annonce}", method = RequestMethod.GET)
	public ModelAndView annonceDetail(@PathVariable(name="annonce") Annonce annonce) {
		
		if(annonce == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Annonce Not Found");
		}
		
		ModelAndView mv = new ModelAndView("detail");
		mv.addObject("annonce", annonce);
		return mv;
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public ModelAndView addCandidatForm() {
		Annonce annonce = new Annonce();		
		ModelAndView mv = new ModelAndView("form");
		mv.addObject("annonce", annonce);
		return mv;
	}

	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String saveCandidat(@Valid Annonce annonce, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "form";
		}
		else {
			annonce.setDatePublication(new Date());
			//annonce.getImage().equals("")
			this.annonceService.saveOrUpdateAnnonce(annonce);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/edit/{annonce}", method = RequestMethod.GET)
	public ModelAndView editCandidat(@PathVariable(name="annonce") Annonce annonce) {
		
		ModelAndView mv = new ModelAndView("form");
		mv.addObject("annonce", annonce);
		return mv;
	}
	
	@RequestMapping(value="/edit/{annonce}", method = RequestMethod.POST)
	public String editCandidat(@Valid Annonce annonce, BindingResult bindingResult) {
		this.annonceService.saveOrUpdateAnnonce(annonce);
		
		if(bindingResult.hasErrors()) {
			return "form";
		}
		else {
			this.annonceService.saveOrUpdateAnnonce(annonce);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/delete/{annonce}", method = RequestMethod.GET)
	public String deleteCandidat(@PathVariable(name="annonce") Annonce annonce) {
		
		this.annonceService.deleteAnnonce(annonce);
		return "redirect:/";
	}
	
/*	@RequestMapping(value="/pdf", method = RequestMethod.GET)
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=annonces_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Annonce> annonces = this.annonceService.getAnnonces();
         
        PDFService exporter = new PDFService(annonces);
        exporter.export(response);         
    }*/
	
	@RequestMapping(value = "/pdf/{annonce}", method = RequestMethod.GET)
    public void pdf(@PathVariable(name="annonce") Annonce annonce, HttpServletResponse response) throws IOException, DocumentException {

        Annonce annonces = this.annonceService.GetAnnonce(annonce.getId());

        this.pdfService.generatePdfFromHtml(annonces);

        InputStream inputStream = new FileInputStream(new File("src/main/resources/static/pdf/Annonce_"+annonce.getId()+".pdf"));
        IOUtils.copy(inputStream, response.getOutputStream());

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=export_annonce numero " +annonce.getId() +" "+ currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        response.flushBuffer();


    }

}
