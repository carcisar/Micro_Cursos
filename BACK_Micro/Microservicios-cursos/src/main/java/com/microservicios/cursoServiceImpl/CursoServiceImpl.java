package com.microservicios.cursoServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.cliente.AlumnoFeignClient;
import com.microservicios.cliente.RespuestaFeingClient;
import com.microservicios.entity.Alumno;
import com.microservicios.entity.Curso;
import com.microservicios.repository.CursoRepository;
import com.microservicios.service.CommonServiceImpl;
import com.microservicios.services.CursoService;

import jakarta.transaction.Transactional;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso,CursoRepository> implements CursoService{
	
	@Autowired
	private RespuestaFeingClient client;
	
	@Autowired
	private AlumnoFeignClient clientAlumno;

	@Override
	@Transactional
	public Curso findByCursoByAlumnoId(Long id) {
		return repository.findByCursoByAlumnoId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumnos(Long alumnoId) {
		return client.obtenerExamenesIdConRespuestasAlumnos(alumnoId);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		return clientAlumno.obtenerAlumnosPorCurso(ids);
	}

	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id);
	}

	

}
