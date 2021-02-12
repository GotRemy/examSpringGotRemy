package com.exam.examSpringGotRemy;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.exam.examSpringGotRemy.model.Annonce;
import com.exam.examSpringGotRemy.repository.AnnonceRepository;

@SpringBootApplication
public class ExamSpringGotRemyApplication {
	
	private org.slf4j.Logger log = LoggerFactory.getLogger(ExamSpringGotRemyApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(ExamSpringGotRemyApplication.class, args);
	}

	@Bean
	public CommandLineRunner testData (AnnonceRepository repository) {
		return (args) ->{
			if(repository.count() == 0) {			
			log.info("- Création d'une Annonce ! -");
			repository.save(new Annonce("https://images.lanouvellerepublique.fr/image/upload/t_1020w/f_auto/5b95be27be7744fb5c8b467b.jpg", "Ceci est un paysage", new Date("2021/02/12")));			
			log.info("- L'annonce est créé ! -");
			}
			else {
				log.info("- Il existe déjà un ou des candidats dans la bdd ! -");
			}
		};
	}
	
	@Bean
	public CommandLineRunner displayApplicationData(AnnonceRepository repository) {
		return (args) ->{
			
			log.info("- Affichage de tous les candidats ! ");
			
			for(Annonce annonce : repository.findAll()) {
				log.info(annonce.toString());
			}

			
		};
	}
}
