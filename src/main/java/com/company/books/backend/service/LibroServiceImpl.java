package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {
	
	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);
	
	@Autowired
	private ILibroDao libroDao; 

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("Inicio del método buscarLibros");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> libros = (List<Libro>) libroDao.findAll();
			response.getLibroResponse().setLibro(libros);
			response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
			
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al consultar libros");
			log.error("Error al consultar libros:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
		log.info("Inicio del método buscarPorId");
		
		LibroResponseRest response = new LibroResponseRest();

		try {
			
			List<Libro> lista = new ArrayList<Libro>();
			Optional<Libro> libro = libroDao.findById(id); 
			
			if (libro.isPresent()) {
				lista.add(libro.get());
				response.getLibroResponse().setLibro(lista);
				response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
			} else {
				log.error("Error al consultar libro");
				response.setMetadata("Respuesta No Ok", "404",	"Libro no encontrada");
				
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
						
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al consultar el libro");
			log.error("Error al consultar el libro:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Inicio del método crear");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> lista = new ArrayList<Libro>();
			Libro libroGuadado = libroDao.save(libro);
			
			if (libroGuadado != null) {
				lista.add(libroGuadado);
				response.getLibroResponse().setLibro(lista);
				response.setMetadata("Respuesta OK", "200", "Libro agregado");
			} else {
				log.error("Error al crear el libro");
				response.setMetadata("Respuesta No Ok", "404",	"Libro no guardado");
				
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al crear el libro");
			log.error("Error al crear el libro:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
		log.info("Inicio del método actualizar");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> lista = new ArrayList<Libro>();
			Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if (libroBuscado.isPresent()) {
			
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setDescripcion(libro.getDescripcion());
				libroBuscado.get().setCategoria(libro.getCategoria());
				
				Libro libroActualizar = libroDao.save(libroBuscado.get());
				
				if (libroActualizar != null) {
					lista.add(libroActualizar);
					response.getLibroResponse().setLibro(lista);
					response.setMetadata("Respuesta OK", "200", "Libro actualizado");
				} else {
					log.error("Error al crear el libro");
					response.setMetadata("Respuesta No Ok", "404",	"Libro no guardado");
					
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
				}

			} else {
				log.error("Error al actualizar el libro");
				response.setMetadata("Respuesta No Ok", "404",	"Libro no encontrada");
				
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
						
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al actualizar el libro");
			log.error("Error al actualizar el libro:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminar(Long id) {
		log.info("Inicio del método eliminar");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			libroDao.deleteById(id);	
			response.setMetadata("Respuesta OK", "200", "Libro eliminado");
			
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al eliminar el libro");
			log.error("Error al eliminar el libro:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

}
