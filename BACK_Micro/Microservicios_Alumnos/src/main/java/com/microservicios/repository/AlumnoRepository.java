package com.microservicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.entity.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long>{
	
	@Query("select a from Alumno a where upper (a.nombre) like upper(concat ( '%', '?1', '%' )) or upper (a.apellido) like upper(concat ( '%', '?1', '%' ))")
	public List<Alumno> findByNombreOrApellido(String term);
	
	

}
