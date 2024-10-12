package com.microservicios.services;

import java.util.List;

import com.microservicios.entity.Asignatura;
import com.microservicios.entity.Examen;
import com.microservicios.service.CommonService;

public interface ExamenService extends CommonService<Examen>{
	
	public List<Examen> findByNombre(String term);
	
	public Iterable<Asignatura> findAllAsignaturas();
	

}
