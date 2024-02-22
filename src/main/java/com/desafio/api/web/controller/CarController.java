package com.desafio.api.web.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api.domain.Car;
import com.desafio.api.service.CarService;
import com.desafio.api.web.dto.CarFormDTO;
import com.desafio.api.web.dto.CarResultDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cars")
@Validated
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping
	public ResponseEntity<CarResultDTO> save(@RequestBody @Validated CarFormDTO carFormDTO,
			HttpServletRequest request) {
		Car car = this.carService.save(Car.buildToCar(carFormDTO));

		String requestUrl = request.getRequestURL().toString();
		String location = requestUrl + "/" + car.getId();

		return ResponseEntity.created(URI.create(location)).body(new CarResultDTO(car));
	}

	@PutMapping("/{id}")
	public ResponseEntity<CarResultDTO> update(@PathVariable Long id,
			@RequestBody @Validated CarFormDTO carFormDTO) {
		Car car = Car.buildToCar(carFormDTO);
		car.setId(id);
		car = this.carService.update(car);
		CarResultDTO carResultDTO = new CarResultDTO(car);
		return ResponseEntity.ok(carResultDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.carService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<CarResultDTO>> list(Pageable pageable) {
		Page<Car> carsPage = this.carService.findAll(pageable);
		return ResponseEntity.ok(carsPage.map(CarResultDTO::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarResultDTO> findById(@PathVariable Long id) {
		Optional<Car> carOptional = this.carService.findById(id);
		if (carOptional.isPresent()) {
			CarResultDTO carResultDTO = new CarResultDTO(carOptional.get());
			return ResponseEntity.ok(carResultDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
