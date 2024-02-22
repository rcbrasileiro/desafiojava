INSERT INTO user_table (first_name, last_name, email, birthday, login, password, phone, created_at, last_login)
VALUES ('João', 'Silva', 'joao@example.com', '1990-01-01', 'joaosilva', '$2a$12$4AOuyX3CZVD8n7Bk9aP2FO/YNxSWKZB7BpUlum/h0QuPJ1ZU1hnmK', '(12) 3456-7890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO car_table (car_year, license_plate, model, color, user_id)
VALUES (2022, 'XYZ-5679', 'Ford Fusion', 'Preto', SELECT MAX(id) from user_table);