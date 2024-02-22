package com.desafio.api.domain;

import java.io.Serializable;

import com.desafio.api.web.dto.CarFormDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car_table")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {

	private static final long serialVersionUID = -4969144777259228472L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "car_year", nullable = false)
	private Integer year;

	@Column(name = "license_plate", length = 255, nullable = false, unique = true)
	private String licensePlate;

	@Column(name = "model", length = 255, nullable = false)
	private String model;

	@Column(name = "color", length = 255, nullable = false)
	private String color;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	public static Car buildToCar(CarFormDTO carFormDTO) {		
		return Car.builder().year(carFormDTO.year()).licensePlate(carFormDTO.licensePlate())
				.model(carFormDTO.model()).color(carFormDTO.color()).build();

	}
}
