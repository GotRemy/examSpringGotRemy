package com.exam.examSpringGotRemy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.examSpringGotRemy.model.Annonce;
import com.exam.examSpringGotRemy.repository.AnnonceRepository;

@Service
public class AnnonceService {

	private AnnonceRepository annonceRepository;

	/**
	 * @param annonceRepository
	 */
	public AnnonceService(AnnonceRepository annonceRepository) {
		super();
		this.annonceRepository = annonceRepository;
	}
	
	public List<Annonce> getAnnonces(){return this.annonceRepository.findAll();}
	
	public Annonce GetAnnonce(Integer id) {return this.annonceRepository.findById(id).orElse(null);}
	
	public void saveOrUpdateAnnonce(Annonce annonce) {this.annonceRepository.save(annonce);}
	
	public void deleteAnnonce (Annonce annonce) {this.annonceRepository.delete(annonce);}
}
