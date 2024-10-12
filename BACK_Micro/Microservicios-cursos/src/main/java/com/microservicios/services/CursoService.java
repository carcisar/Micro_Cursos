package com.microservicios.services;

import java.util.List;


import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.entity.Alumno;
import com.microservicios.entity.Curso;
import com.microservicios.service.CommonService;

public interface CursoService extends CommonService<Curso>{
	
	public Curso findByCursoByAlumnoId(Long id);
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumnos(Long alumnoId);
	public Iterable<Alumno> obtenerAlumnosPorCurso( List<Long> ids);
	public void eliminarCursoAlumnoPorId(Long id);

}
