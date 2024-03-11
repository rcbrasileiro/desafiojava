package com.desafio.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.desafio.api.domain.Car;

public interface CarService {

	Car save(Car car);

	Car update(Car car);

	Optional<Car> findById(Long id);

	Page<Car> findAll(Pageable pageable);

	void delete(Long id);

	boolean existsByLicensePlate(String licensePlate);

	void validateFields(Car car);
	
	Car findByLicensePlate(String licensePlate);
	
	long defineCarId(Car car);
	
	Page<Car> findAllByUserId(Pageable pageable);
	
}
