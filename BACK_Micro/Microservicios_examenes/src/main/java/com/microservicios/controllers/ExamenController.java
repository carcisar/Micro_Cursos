package com.microservicios.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.Examen;
import com.microservicios.entity.Pregunta;
import com.microservicios.services.ExamenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/examenes")
public class ExamenController extends CommonController<Examen, ExamenService>{
	
	
//	@GetMapping("/test")
//	public ResponseEntity<String> test() {
//	    return ResponseEntity.ok("El servicio de exámenes está funcionando.");
//	}

	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result,  @PathVariable Long id ){
		if (result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Examen> o = service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Examen examenDb = o.get();
		examenDb.setNombre(examen.getNombre());
		
//		examenDb.getPreguntas().stream().filter(pdb -> !examen.getPreguntas().contains(pdb))
//		.forEach(examenDb::removePregunta);
		
		//Forma sin stream()
		List<Pregunta> eliminadas = new ArrayList<>();
		examenDb.getPreguntas().forEach(pd ->{
			if (!examen.getPreguntas().contains(pd)) {
				eliminadas.add(pd);
			}
		});
		
		eliminadas.forEach(p -> {
			examenDb.removePregunta(p);
		});
		
		examenDb.setPreguntas(examen.getPreguntas());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
		
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar (@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
