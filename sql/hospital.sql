DROP DATABASE IF EXISTS hospital;
CREATE DATABASE IF NOT EXISTS hospital DEFAULT CHARACTER SET latin1;
USE hospital;

CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    patronymic VARCHAR(50),
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) BINARY NOT NULL,
    user_type VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (login)
)
ENGINE = InnoDB
AUTO_INCREMENT = 2,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE admin (
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    position VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id),
    FOREIGN KEY (user_id) REFERENCES hospital.user (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 101,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE patient (
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id, phone),
    FOREIGN KEY (user_id) REFERENCES hospital.user (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 202,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE doctor_speciality (
    id INT NOT NULL AUTO_INCREMENT,
    speciality VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
)
ENGINE = InnoDB
AUTO_INCREMENT = 303,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE doctor_room (
    id INT NOT NULL AUTO_INCREMENT,
    room VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
)
ENGINE = InnoDB
AUTO_INCREMENT = 80,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE doctor (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    speciality_id INT NOT NULL,
    room_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES hospital.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (speciality_id) REFERENCES hospital.doctor_speciality (id) ON DELETE NO ACTION ON UPDATE CASCADE,
    FOREIGN KEY (room_id) REFERENCES hospital.doctor_room (id) ON DELETE NO ACTION ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 505,
DEFAULT CHARACTER SET = utf8;

CREATE TABLE daySchedule (
	id INT NOT NULL AUTO_INCREMENT,
    doctor_id INT NOT NULL,
	date DATE NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (doctor_id, date),
    FOREIGN KEY (doctor_id) REFERENCES hospital.doctor (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 606,
DEFAULT CHARACTER SET = latin1;

CREATE TABLE appointment (
	id INT NOT NULL AUTO_INCREMENT,
    schedule_id INT NOT NULL,
    time_slot VARCHAR(10) NOT NULL,
    duration INT NOT NULL,
    patient_id INT,
    status VARCHAR(50) NOT NULL DEFAULT 'FREE',
    PRIMARY KEY (id),
    UNIQUE KEY (schedule_id, time_slot),
    FOREIGN KEY (schedule_id) REFERENCES hospital.daySchedule (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 707,
DEFAULT CHARACTER SET = latin1;

CREATE TABLE commission (
	id INT NOT NULL AUTO_INCREMENT,
    patient_id INT NOT NULL,
    room_id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    duration INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (patient_id, room_id),
    FOREIGN KEY (patient_id) REFERENCES hospital.patient (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (room_id) REFERENCES hospital.doctor_room (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 808,
DEFAULT CHARACTER SET = latin1;

CREATE TABLE doctor_commission (
	id INT NOT NULL AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    commission_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (doctor_id, commission_id),
    FOREIGN KEY (doctor_id) REFERENCES hospital.doctor (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (commission_id) REFERENCES hospital.commission (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 909,
DEFAULT CHARACTER SET = latin1;

CREATE TABLE patient_ticket (
    id INT NOT NULL AUTO_INCREMENT,
    patient_id INT NULL,
    number VARCHAR(50) NOT NULL,
    daySchedule_id INT,
    commission_id INT,
    time_start TIME NOT NULL,
    time_end TIME NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'FREE',
    PRIMARY KEY (id),
    UNIQUE KEY (number),
    UNIQUE(daySchedule_id, time_start),
    UNIQUE(daySchedule_id, patient_id),
    FOREIGN KEY (patient_id) REFERENCES hospital.patient (id) ON DELETE SET NULL,
    FOREIGN KEY (daySchedule_id) REFERENCES hospital.daySchedule (id) ON DELETE CASCADE,
    FOREIGN KEY (commission_id) REFERENCES hospital.commission (id) ON DELETE CASCADE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 909,
    DEFAULT CHARACTER SET = latin1;

CREATE TABLE session (
    id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
    cookie VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES hospital.user (id) ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB
AUTO_INCREMENT = 55,
DEFAULT CHARACTER SET = latin1;

INSERT INTO doctor_room(room) VALUES ("101"), ("102"), ("103"), ("103A"), ("104"), ("201"), ("202"), ("203"), ("204"), ("204B");

INSERT INTO doctor_speciality(speciality) VALUES ("Allergist"), ("Otolaryngologist"), ("Cardiologist"), ("Endocrinologist"),
                                                 ("Therapist"), ("Gynecologist"), ("Neurologist"), ("Oncologist"),
                                                 ("Surgeon"), ("Psychiatrist"), ("Urologist");

INSERT INTO user(first_name, last_name, patronymic, login, password, user_type) VALUES ("Иван", "Иванов", "Иванович",
                                                                                        "admin", "vv1aa2nn3ee4kk5", "ADMIN");
INSERT INTO admin(user_id, position) VALUES (LAST_INSERT_ID(), "Super Admin");

