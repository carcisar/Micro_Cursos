package com.microservicios.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.entity.Asignatura;
import com.microservicios.entity.Examen;
import com.microservicios.repository.AsignaturaRepository;
import com.microservicios.repository.ExamenRepository;
import com.microservicios.service.CommonServiceImpl;
import com.microservicios.services.ExamenService;

import jakarta.transaction.Transactional;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen, ExamenRepository> implements ExamenService {
	
	@Autowired
	private AsignaturaRepository asignaturaRepository;

	@Override
	@Transactional
	public List<Examen> findByNombre(String term) {
		return repository.findByNombre(term);
	}

	@Override
	@Transactional
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepository.findAll();
	}


}
