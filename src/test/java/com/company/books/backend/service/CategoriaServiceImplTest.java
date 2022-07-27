package com.company.books.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;

public class CategoriaServiceImplTest {

	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	ICategoriaDao categoriaDao;
	
	List<Categoria> categorias = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		this.cargarCategorias();
	}
	
	@Test
	public void buscarCategoriasTest() {
		
		when(categoriaDao.findAll()).thenReturn(categorias);
		var response = service.buscarCategorias();
		
		assertEquals(response.getBody().getCategoriaResponse().getCategoria().size(), this.categorias.size());
		verify(categoriaDao, times(1)).findAll();
	}
	
	public void cargarCategorias() {
		Categoria categoria1 = new Categoria(Long.valueOf(1), "Abarrotes", "Distintos abarrotes");
		Categoria categoria2 = new Categoria(Long.valueOf(2), "Lácteos", "Variedad de lácteos");
		Categoria categoria3 = new Categoria(Long.valueOf(3), "Bebidas", "Bebidas sin azucar");
		Categoria categoria4 = new Categoria(Long.valueOf(4), "Carnes blancas", "Distintas carnes");
		
		categorias.add(categoria1);
		categorias.add(categoria2);
		categorias.add(categoria3);
		categorias.add(categoria4);
	}
	
	
}
