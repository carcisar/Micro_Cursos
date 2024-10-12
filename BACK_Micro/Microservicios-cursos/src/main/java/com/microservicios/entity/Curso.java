package com.microservicios.entity;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cursos")
public class Curso {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@Column(name = "create_At")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@JsonIgnoreProperties(value = {"curso"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy ="curso", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<CursoAlumno> cursoAlumnos;
	
//	@OneToMany(fetch = FetchType.LAZY)
	@Transient
	private List<Alumno> alumnos;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Examen> examenes;
	
	
//	public Curso() {
//		
//	}


	public Curso() {
		this.alumnos = new ArrayList<>();
		this.examenes = new ArrayList<>();
		this.cursoAlumnos = new ArrayList<>();
	}


	public Curso(Long id, String name, Date createAt, List<Alumno> alumnos) {
		super();
		this.id = id;
		this.name = name;
		this.createAt = createAt;
		this.alumnos = alumnos;
	}




	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public List<Alumno> getAlumnos() {
		return alumnos;
	}


	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	
	
	
	public List<Examen> getExamenes() {
		return examenes;
	}


	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}


	public void addAlumnos(Alumno alumno) {
		this.alumnos.add(alumno);
	}
	
	public void removeAlumnos(Alumno alumno) {
		this.alumnos.remove(alumno);
	}
	
	
	public void addExamen(Examen examen) {
		this.examenes.add(examen);
	}
	
	public void removeExamen(Examen examen) {
		this.examenes.remove(examen);
	}


	public List<CursoAlumno> getCursoAlumnos() {
		return cursoAlumnos;
	}


	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}
	
	public void addCursoAlumnos(CursoAlumno cursoAlumnos) {
		this.cursoAlumnos.add(cursoAlumnos);
	}
	
	public void removeCursoAlumnos(CursoAlumno cursoAlumnos) {
		this.cursoAlumnos.remove(cursoAlumnos);
	}
	
}
