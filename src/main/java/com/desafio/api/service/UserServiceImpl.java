package com.desafio.api.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.api.domain.Car;
import com.desafio.api.domain.User;
import com.desafio.api.repository.UserRepository;
import com.desafio.api.security.config.SecurityConfig;
import com.desafio.api.service.exception.EmailAlreadyExistsException;
import com.desafio.api.service.exception.InvalidFieldsException;
import com.desafio.api.service.exception.LoginAlreadyExistsException;
import com.desafio.api.util.LoggedUser;

import jakarta.transaction.Transactional;

@Service
@Primary
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarService carService;

	@Autowired
	private SecurityConfig securityConfig;

	@Override
	@Transactional
	public User save(User user) {
		this.validateFields(user);
		user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
		user.getCars().forEach(car -> car.setUser(user));
		return this.userRepository.save(user);
	}

	@Override
	@Transactional
	public User update(User user) {
		user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
		user.getCars().forEach(car -> {
			car.setId(carService.defineCarId(car));
			car.setUser(user);
		});
		this.validateFields(user);
		return this.userRepository.save(user);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable);
	}

	@Override
	public void delete(Long id) {
		this.userRepository.deleteById(id);

	}

	@Override
	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public void updateLastLogin() {
		User user = LoggedUser.getUser();
		user.setLastLogin(new Date());
		this.userRepository.save(user);
	}

	private void validateFields(User user) {
		if (isNewUser(user) && StringUtils.isNotBlank(user.getEmail())) {
			if (userRepository.existsByEmail(user.getEmail())) {
				throw new EmailAlreadyExistsException("Email already exists");
			}
		}

		if (isNewUser(user) && StringUtils.isNotBlank(user.getLogin())) {
			if (userRepository.existsByLogin(user.getLogin())) {
				throw new LoginAlreadyExistsException("Login already exists");
			}
		}

		if (!isNewUser(user) && (Objects.nonNull(user.getId()) && user.getId() > 0)) {
			if (!userRepository.existsById(user.getId())) {
				throw new InvalidFieldsException("Invalid user id");
			}
		}

		Set<String> seenLicensePlates = new HashSet<String>();

		List<Car> uniqueCars = user.getCars().stream().filter(carro -> seenLicensePlates.add(carro.getLicensePlate()))
				.collect(Collectors.toList());

		user.setCars(uniqueCars);

		user.getCars().forEach(carService::validateFields);
	}

	private boolean isNewUser(User user) {
		return user.getId() == null || user.getId() <= 0;
	}
}
