package com.microservicios.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.Alumno;
import com.microservicios.entity.Curso;
import com.microservicios.entity.CursoAlumno;
import com.microservicios.entity.Examen;
import com.microservicios.services.CursoService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<Curso, CursoService>{
	
	
	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoPorAlumnoPorId(@PathVariable Long id){
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	private ResponseEntity<?> listar(){
		List<Curso> cursos =( (List<Curso>) service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(ca ->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumnos(alumno);
			});
			return c;
		}).collect(Collectors.toList());
	    return ResponseEntity.ok().body(cursos);
	}
	
	@GetMapping("/pagina-detalle")
	private ResponseEntity<?> listarPaginacion(Pageable pageable){
		org.springframework.data.domain.Page<Curso> cursos = service.findAll(pageable).map(curso ->{
			curso.getCursoAlumnos().forEach(ca ->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				curso.addAlumnos(alumno);
			});
			return curso;
		});
	    return ResponseEntity.ok().body(cursos);
	}
	

	@GetMapping("/{id}/ver")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Curso> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso curso = o.get();
		if (curso.getCursoAlumnos().isEmpty() == false) {
			List<Long> ids = curso.getCursoAlumnos().stream().map( ca  ->  ca.getAlumnoId()).collect(Collectors.toList());
			List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumnos);
		}
		return ResponseEntity.ok().body(curso);
	}
	
	
	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<>();
		response.put("balanceador", balanceadorTest);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result,@PathVariable Long id ){
		if (result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		cursoDb.setName(curso.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
		
	}
	

	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id ){
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		alumnos.forEach(a -> {
			
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(cursoDb);
			cursoDb.addCursoAlumnos(cursoAlumno);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id ){
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		cursoDb.removeCursoAlumnos(cursoAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> findByCursoByAlumnoId(@PathVariable Long id) {
		Curso curso = service.findByCursoByAlumnoId(id);
		if (curso != null) {
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdConRespuestasAlumnos(id);
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
				if (examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			curso.setExamenes(examenes);
		}
		return ResponseEntity.ok(curso);
		
	}


	

	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id ){
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		examenes.forEach(a -> {
			cursoDb.addExamen(a);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id ){
		Optional<Curso> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursoDb = o.get();
		cursoDb.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
