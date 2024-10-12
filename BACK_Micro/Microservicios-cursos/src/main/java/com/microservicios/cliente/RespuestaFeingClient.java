package com.microservicios.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Microservicios-respuestas", path = "/api/respuestas")
public interface RespuestaFeingClient {
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesIdConRespuestasAlumnos(@PathVariable Long alumnoId);
		
	

}
