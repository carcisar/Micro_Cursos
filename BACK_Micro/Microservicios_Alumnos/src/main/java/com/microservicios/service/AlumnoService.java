package com.microservicios.service;

import java.util.List;


import com.microservicios.entity.Alumno;



public interface AlumnoService extends CommonService<Alumno>{
	
	
	public List<Alumno> findByNombreOrApellido(String term);
	public Iterable<Alumno> findAllbById(Iterable<Long> ids);
	public void  eliminarCursoPorAlumnoPorId( Long id);

}
