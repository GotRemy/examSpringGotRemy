package com.exam.examSpringGotRemy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.exam.examSpringGotRemy.model.Annonce;


@Repository
public interface AnnonceRepository extends CrudRepository<Annonce, Integer>{

	List<Annonce> findAll();
}
