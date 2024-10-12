package com.microservicios.serviceImp;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.client.CursoFeignClient;
import com.microservicios.entity.Alumno;
import com.microservicios.repository.AlumnoRepository;
import com.microservicios.service.AlumnoService;
import com.microservicios.service.CommonServiceImpl;

import jakarta.transaction.Transactional;


@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService{
	
	@Autowired
	private CursoFeignClient clientCurso;

	@Override
	@Transactional
	public List<Alumno> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional
	public Iterable<Alumno> findAllbById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoPorAlumnoPorId(Long id) {
		clientCurso.eliminarCursoPorAlumnoPorId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarCursoPorAlumnoPorId(id);
	}

	
	
	

}
