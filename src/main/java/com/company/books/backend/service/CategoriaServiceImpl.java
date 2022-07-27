package com.company.books.backend.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		log.info("Inicio método buscarCategorias()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			
			response.getCategoriaResponse().setCategoria(categoria);
			
			response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
			
		} catch (Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al consultar categorías");
			log.error("Error al consultar categorías:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("Inicio método buscarPorId");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> lista = new ArrayList<Categoria>();
			Optional<Categoria> categoria = categoriaDao.findById(id);
			
			if (categoria.isPresent()) {
				lista.add(categoria.get());
				response.getCategoriaResponse().setCategoria(lista);
				
				response.setMetadata("Respuesta OK", "200", "Respuesta Exitosa");
			} else {
				log.error("Error al consultar categoria");
				response.setMetadata("Respuesta No Ok", "404",	"Categoria no encontrada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch(Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al consultar categoría");
			log.error("Error al consultar categoría:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("Inicio método crear categoria");
		
		CategoriaResponseRest response = new CategoriaResponseRest();		
		
		try {
			List<Categoria> lista = new ArrayList<Categoria>();
			Categoria categoriaGuardada = categoriaDao.save(categoria);
			
			if (categoriaGuardada != null) {
				lista.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(lista);
				response.setMetadata("Respuesta OK", "200", "Categoria agregada");
			} else {
				log.error("Error al crear categoria");
				response.setMetadata("Respuesta No Ok", "404",	"Categoria no guardada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch(Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al crear categoría");
			log.error("Error al crear categoría:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("Inicio método actualizar");
		
		CategoriaResponseRest response = new CategoriaResponseRest();		
		
		try {
			List<Categoria> lista = new ArrayList<>();
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if(categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());
				
				if (categoriaActualizar != null) {
				    lista.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(lista);
					response.setMetadata("Respuesta OK", "200", "Categoria actualizada");
				} else {
					log.error("Error al actualizar la categoria");
					response.setMetadata("Respuesta No Ok", "404",	"Categoria no guardada");
					
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				log.error("Error al actualizar la categoria");
				response.setMetadata("Respuesta No Ok", "404",	"Categoria no encontrada");
				
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch(Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al actualizar categoría");
			log.error("Error al actualizar categoría:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}

		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inicio método eliminar categoria");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			categoriaDao.deleteById(id);	
			response.setMetadata("Respuesta OK", "200", "Categoria eliminada");
			
		} catch(Exception ex) {
			response.setMetadata("Respuesta No OK", "500", "Error al eliminar la categoría");
			log.error("Error al eliminar la categoría:", ex.getMessage());
			ex.getStackTrace();
			
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK);
	}
	
}
