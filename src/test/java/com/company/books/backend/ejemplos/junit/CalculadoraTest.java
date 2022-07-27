package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	private Calculadora cal;
	
	@BeforeAll
	public static void beforeAll() {
		System.out.println("beforeAll...");
	}
	
	@AfterAll
	public static void afterAll() {
		System.out.println("afterAll...");		
	}
	
	@BeforeEach
	public void beforeEach() {
		cal  = new Calculadora();
		System.out.println("beforeEach...");
	}
	
	@AfterEach
	public void afterEach() {
		cal = null;
		System.out.println("afterEach...");
	}
	
	@Test
	@DisplayName("Test Calculadora AssertEqual")
//	@Disabled
	public void calculadoraAssertEqualTest() {	
		
		assertEquals(4, cal.sumar(2, 2));
		assertEquals(8, cal.restar(10, 2));
		assertEquals(25, cal.multiplicar(5, 5));
		assertEquals(5, cal.dividir(25, 5));
		
		System.out.println("calculadoraAssertEqualTest...");
	}
	
	@Test
	@DisplayName("Test Calculadora AssertTrueFalse")
	public void calculadoraAssertTrueFalseTest() {
			
		assertTrue(cal.sumar(2, 2) == 4);
		assertTrue(cal.restar(4, 1) == 3);
		assertFalse(cal.multiplicar(3, 8) == 8);
		assertFalse(cal.dividir(9, 5) == 3);
		
		System.out.println("calculadoraAssertTrueFalseTest...");
	}

}
