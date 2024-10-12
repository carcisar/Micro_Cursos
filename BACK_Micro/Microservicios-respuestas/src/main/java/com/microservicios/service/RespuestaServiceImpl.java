package com.microservicios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.entity.Respuesta;
import com.microservicios.repository.RespuestaRepository;

import jakarta.transaction.Transactional;

@Service
public class RespuestaServiceImpl implements RespuestaService{
	
	@Autowired
	private RespuestaRepository repository;

	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	@Transactional
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
	}

	@Override
	@Transactional
	public Iterable<Long> findExamenesIdsConRespuestaByAlumno(Long alumnoId) {
		return repository.findExamenesIdsConRespuestaByAlumno(alumnoId);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
