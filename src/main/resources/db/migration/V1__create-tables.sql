CREATE TABLE user_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP
);

CREATE TABLE car_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_year INT NOT NULL,
    license_plate VARCHAR(255) NOT NULL UNIQUE,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_table(id)
);

