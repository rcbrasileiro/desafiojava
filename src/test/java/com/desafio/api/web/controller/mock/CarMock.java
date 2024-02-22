package com.desafio.api.web.controller.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.desafio.api.domain.Car;

public class CarMock {

	public static Car create() {
		Car car = mock(Car.class);
		when(car.getId()).thenReturn(1L);
		when(car.getYear()).thenReturn(2022);
		when(car.getLicensePlate()).thenReturn("XYZ-5679");
		when(car.getModel()).thenReturn("Ford Fusion");
		when(car.getColor()).thenReturn("Preto");
		return car;
	}
	
	public static Car create2() {
		Car car = mock(Car.class);
		when(car.getId()).thenReturn(2L);
		when(car.getYear()).thenReturn(2023);
		when(car.getLicensePlate()).thenReturn("ABC-5679");
		when(car.getModel()).thenReturn("BMW 320");
		when(car.getColor()).thenReturn("Azul");
		return car;
	}
}
