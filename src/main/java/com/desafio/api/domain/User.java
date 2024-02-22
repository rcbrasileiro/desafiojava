package com.desafio.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;

import com.desafio.api.web.dto.UserFormDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Tag(name = "Usuário", description = "Entidade que representa o usuário")
@Entity
@Table(name = "user_table")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
		
	private static final long serialVersionUID = 3529950425908848709L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name", length = 255, nullable = false)
	private String firstName;

	@Column(name = "last_name", length = 255, nullable = false)
	private String lastName;

	@Column(name = "email", length = 255, nullable = false)
	private String email;

	@Column(name = "birthday", length = 255, nullable = false)
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "login", length = 255, nullable = false)
	private String login;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "phone", length = 255, nullable = false)
	private String phone;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", columnDefinition = "TIMESTAMP", nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login", columnDefinition = "TIMESTAMP")
	private Date lastLogin;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Car> cars;
	
	@PrePersist
	public void onPrePersist() {
		this.createdAt = new Date();
	}	

	public static User buildToUser(UserFormDTO userFormDTO) {

		List<Car> cars = userFormDTO.cars().stream().map(Car::buildToCar).collect(Collectors.toList());

		return User.builder().firstName(userFormDTO.firstName()).lastName(userFormDTO.lastName())
				.email(userFormDTO.email()).birthday(userFormDTO.birthday()).login(userFormDTO.login())
				.password(userFormDTO.password()).phone(userFormDTO.phone()).cars(cars).build();
	}
}
