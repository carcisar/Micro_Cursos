package com.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.entity.Asignatura;

public interface AsignaturaRepository  extends JpaRepository<Asignatura, Long>{

}
