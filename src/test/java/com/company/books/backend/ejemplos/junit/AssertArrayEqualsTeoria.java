package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class AssertArrayEqualsTeoria {

	@Test
	public void pruebaArreglosIguales() {
		String[] array1 = { "a", "b" };
		String[] array2 = { "a", "b" };
//		String[] array3 = { "a", "b", "c"};

		assertArrayEquals(array1, array2);
//		assertArrayEquals(array1, array3);
//		assertArrayEquals(array2, array3);
	}
}
