package com.meli.app.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.app.dto.UrlDTO;
import com.meli.app.rest.cache.ShortenerCache;

@RestController
@RequestMapping("/meli")
public class MyRestController {

	@PostMapping("/urlshort")
	public UrlDTO urlshort(@RequestBody UrlDTO url) {
		final UrlDTO respuesta = new UrlDTO();
		if(url.getUrlOriginal() != null && !url.getUrlOriginal().isEmpty()) {
			respuesta.setUrlCorta(ShortenerCache.getUrlShort(url.getUrlOriginal()));	
		}
		if(url.getUrlCorta() != null && !url.getUrlCorta().isEmpty()) {
			respuesta.setUrlOriginal(ShortenerCache.getUrlOriginal(url.getUrlCorta()));	
		}
		
		return respuesta;
	}
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteUser(@PathVariable String id) {
	        ShortenerCache.deleteUrlShort(id);
	        return ResponseEntity.ok("Url Corta eliminada exitosamente");
	    } 
}
