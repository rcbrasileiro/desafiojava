package com.desafio.api.service;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.api.domain.Car;
import com.desafio.api.domain.User;
import com.desafio.api.repository.CarRepository;
import com.desafio.api.service.exception.InvalidFieldsException;
import com.desafio.api.service.exception.LicensePlateAlreadyExistsException;
import com.desafio.api.util.LoggedUser;

import jakarta.transaction.Transactional;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;

	@Override
	@Transactional
	public Car save(Car car) {
		this.validateFields(car);
		User user = LoggedUser.getUser();
		car.setUser(user);
		return this.carRepository.save(car);
	}

	@Override
	@Transactional
	public Car update(Car car) {
		User user = LoggedUser.getUser();
		car.setUser(user);
		this.validateFields(car);
		return this.carRepository.save(car);
	}

	@Override
	public Page<Car> findAll(Pageable pageable) {
		return this.carRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.carRepository.deleteById(id);

	}

	@Override
	public Optional<Car> findById(Long id) {
		return this.carRepository.findById(id);
	}

	@Override
	public boolean existsByLicensePlate(String licensePlate) {
		return this.carRepository.existsByLicensePlate(licensePlate);
	}

	public void validateFields(Car car) {
		if (car.getYear() <= 0) {
			throw new InvalidFieldsException("Invalid fields");
		}

		if (isNewCar(car) && StringUtils.isNotBlank(car.getLicensePlate())) {
			if (existsByLicensePlate(car.getLicensePlate())) {
				throw new LicensePlateAlreadyExistsException("License plate already exists");
			}
		}
		
		if (!isNewCar(car) && (Objects.nonNull(car.getId()) && car.getId() > 0)) {
			if (!carRepository.existsById(car.getId())) {
				throw new InvalidFieldsException("Invalid car id");
			}
		}
	}

	private boolean isNewCar(Car car) {
		return car.getId() == null || car.getId() <= 0;
	}

	@Override
	public Car findByLicensePlate(String licensePlate) {
		return this.carRepository.findByLicensePlate(licensePlate);
	}

	@Override
	public long defineCarId(Car car) {
		Car carSaved = this.findByLicensePlate(car.getLicensePlate());
		if(Objects.nonNull(carSaved)) {
			return carSaved.getId();
		}
		return 0L;
	}
	
}
