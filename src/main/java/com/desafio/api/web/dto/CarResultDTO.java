package com.desafio.api.web.dto;

import com.desafio.api.domain.Car;

public record CarResultDTO(Long id, Integer year, String licensePlate, String model, String color) {
	public CarResultDTO(Car car) {
		this(car.getId(), car.getYear(), car.getLicensePlate(), car.getModel(), car.getColor());
	}
}
