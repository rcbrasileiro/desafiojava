package com.desafio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.api.domain.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

	boolean existsByLicensePlate(String licensePlate);
	
	Car findByLicensePlate(String licensePlate);

}
