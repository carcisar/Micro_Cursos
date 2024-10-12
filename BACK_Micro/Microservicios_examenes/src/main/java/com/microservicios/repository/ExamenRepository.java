package com.microservicios.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.entity.Examen;


@Repository
@Primary
public interface ExamenRepository extends JpaRepository<Examen, Long>{
	
	@Query("select e from Examen e where e.nombre like %?1%")
	public List<Examen> findByNombre(String term);

}
