CREATE TABLE owners (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        email VARCHAR(255) DEFAULT NULL,
                        name VARCHAR(255) DEFAULT NULL,
                        phone VARCHAR(255) DEFAULT NULL,
                        PRIMARY KEY (id)
);

CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       email VARCHAR(255) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       owner_id BIGINT DEFAULT NULL,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'USER') NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY uk_users_email (email)
);

CREATE TABLE vaccines (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          description VARCHAR(255) DEFAULT NULL,
                          name VARCHAR(255) DEFAULT NULL,
                          recommended_interval_months INT DEFAULT NULL,
                          species ENUM('CAT', 'DOG') DEFAULT NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE pets (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      birth_date DATE DEFAULT NULL,
                      breed VARCHAR(255) DEFAULT NULL,
                      name VARCHAR(255) DEFAULT NULL,
                      sex VARCHAR(255) DEFAULT NULL,
                      species ENUM('CAT', 'DOG') DEFAULT NULL,
                      owner_id BIGINT NOT NULL,
                      PRIMARY KEY (id),

                      CONSTRAINT fk_pets_owner
                          FOREIGN KEY (owner_id)
                              REFERENCES owners(id)
);

CREATE TABLE vaccinations (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              application_date DATE DEFAULT NULL,
                              next_dose_date DATE DEFAULT NULL,
                              notes VARCHAR(255) DEFAULT NULL,

                              pet_id BIGINT NOT NULL,

                              vaccine_id BIGINT DEFAULT NULL,

                              custom_vaccine_name VARCHAR(255) DEFAULT NULL,

                              source ENUM('CATALOG', 'EXTERNAL') NOT NULL,

                              PRIMARY KEY (id),

                              CONSTRAINT fk_vaccinations_pet
                                  FOREIGN KEY (pet_id)
                                      REFERENCES pets(id),

                              CONSTRAINT fk_vaccinations_vaccine
                                  FOREIGN KEY (vaccine_id)
                                      REFERENCES vaccines(id)
);