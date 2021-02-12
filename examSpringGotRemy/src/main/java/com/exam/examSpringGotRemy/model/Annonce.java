package com.exam.examSpringGotRemy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="annonce")
@Table(name="annonce")
public class Annonce {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
    @Column(name = "image", length=255)
	private String image;
	
	@NotBlank(message="Veuillez saisir un contenu !")
    @Column(name = "contenu", nullable = false, length=255)
	@Size(min = 20)
	private String contenu;
	
	@Column(name = "datePublication")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date datePublication;

	/**
	 * @param id
	 * @param image
	 * @param contenu
	 * @param datePublication
	 */
	public Annonce(int id, String image, @NotBlank(message = "Veuillez saisir un contenu !") String contenu,
			Date datePublication) {
		super();
		this.id = id;
		this.image = image;
		this.contenu = contenu;
		this.datePublication = datePublication;
	}

	/**
	 * @param image
	 * @param contenu
	 * @param datePublication
	 */
	public Annonce(String image, @NotBlank(message = "Veuillez saisir un contenu !") String contenu,
			Date datePublication) {
		super();
		this.image = image;
		this.contenu = contenu;
		this.datePublication = datePublication;
	}
	
	public Annonce() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	@Override
	public String toString() {
		return "Annonce [id=" + id + ", image=" + image + ", contenu=" + contenu + ", datePublication="
				+ datePublication + "]";
	}
	
	
}
