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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Gestão do carro", description = "API de gestão dos carros")
@RestController
@RequestMapping("/api/cars")
@Validated
public class CarController {

	@Autowired
	private CarService carService;

	@Operation(
		      summary = "Salvar",
		      description = "Salva um novo carro")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session"),
	      @ApiResponse(responseCode = "409", description = "License plate already exists"),
	      @ApiResponse(responseCode = "400", description = "Invalid fields"),
	      @ApiResponse(responseCode = "422", description = "Missing fields")})
	@PostMapping
	public ResponseEntity<CarResultDTO> save(@RequestBody @Validated CarFormDTO carFormDTO,
			HttpServletRequest request) {
		Car car = this.carService.save(Car.buildToCar(carFormDTO));

		String requestUrl = request.getRequestURL().toString();
		String location = requestUrl + "/" + car.getId();

		return ResponseEntity.created(URI.create(location)).body(new CarResultDTO(car));
	}

	@Operation(
		      summary = "Atualizar",
		      description = "Atualizar um carro")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session"),
	      @ApiResponse(responseCode = "409", description = "License plate already exists"),
	      @ApiResponse(responseCode = "400", description = "Invalid fields"),
	      @ApiResponse(responseCode = "422", description = "Missing fields")})
	@PutMapping("/{id}")
	public ResponseEntity<CarResultDTO> update(@PathVariable Long id, @RequestBody @Validated CarFormDTO carFormDTO) {
		Car car = Car.buildToCar(carFormDTO);
		car.setId(id);
		car = this.carService.update(car);
		CarResultDTO carResultDTO = new CarResultDTO(car);
		return ResponseEntity.ok(carResultDTO);
	}

	@Operation(
		      summary = "Deletar",
		      description = "Deletar um carro")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session")})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.carService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(
		      summary = "Listar",
		      description = "Listar todos os carros")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session")})
	@GetMapping
	public ResponseEntity<Page<CarResultDTO>> list(Pageable pageable) {
		Page<Car> carsPage = this.carService.findAllByUserId(pageable);
		return ResponseEntity.ok(carsPage.map(CarResultDTO::new));
	}

	@Operation(
		      summary = "Buscar",
		      description = "Buscar por id")
	 @ApiResponses({
	      @ApiResponse(responseCode = "401", description = "Unauthorized"),
	      @ApiResponse(responseCode = "401", description = "Unauthorized - invalid session")})
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
