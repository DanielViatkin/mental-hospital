create table chamber_types
(
    id                        int auto_increment
        primary key,
    title                     varchar(45)  not null,
    num_of_beds               int          not null,
    price                     int          null,
    num_of_available_chambers int          not null,
    img_ref                   varchar(250) not null
);


create table diseases
(
	id int auto_increment
		primary key,
	name varchar(70) not null,
	description varchar(3000) not null
);

create table drugs
(
	id int auto_increment
		primary key,
	name varchar(45) not null
);

create table hospitals
(
	id int auto_increment
		primary key,
	address varchar(100) not null,
	phone_number varchar(45) not null
);

create table chambers
(
	id int auto_increment
		primary key,
	chamber_type_id int not null,
	hospital_id int null,
	num_free_beds int null,
	constraint fk_chambers_chambres_type1
		foreign key (chamber_type_id) references chamber_types (id),
	constraint fk_chambers_hospital1
		foreign key (hospital_id) references hospitals (id)
);

create index fk_chambers_chambres_type1_idx
	on chambers (chamber_type_id);

create index fk_chambers_hospital1_idx
	on chambers (hospital_id);

create table role
(
	id int auto_increment
		primary key,
	role_name varchar(45) not null,
	constraint name_UNIQUE
		unique (role_name)
);

create table treatment_courses
(
	id int auto_increment
		primary key,
	instructions varchar(500) not null
);

create table disease_symptoms
(
	treatment_course_id int not null,
	diseases_id int not null,
	symptoms varchar(200) not null,
	constraint fk_treatment_course_has_diseases_diseases1
		foreign key (diseases_id) references diseases (id),
	constraint fk_treatment_course_has_diseases_treatment_course1
		foreign key (treatment_course_id) references treatment_courses (id)
);

create index fk_treatment_course_has_diseases_diseases1_idx
	on disease_symptoms (diseases_id);

create index fk_treatment_course_has_diseases_treatment_course1_idx
	on disease_symptoms (treatment_course_id);

create table drug_recipes
(
	treatment_course_id int not null,
	drug_id int not null,
	description varchar(400) not null,
	dose float not null,
	constraint fk_treatment_courses_has_drugs_drugs1
		foreign key (drug_id) references drugs (id),
	constraint fk_treatment_courses_has_drugs_treatment_courses1
		foreign key (treatment_course_id) references treatment_courses (id)
);

create index fk_treatment_courses_has_drugs_drugs1_idx
	on drug_recipes (drug_id);

create index fk_treatment_courses_has_drugs_treatment_courses1_idx
	on drug_recipes (treatment_course_id);

create table user_roles
(
	id int not null
		primary key,
	name varchar(45) not null
);

create table users
(
	id int auto_increment
		primary key,
	user_role_id int not null,
	email varchar(45) not null,
	password varchar(550) not null,
	first_name varchar(45) not null,
	last_name varchar(45) not null,
	number varchar(45) not null,
	is_banned tinyint(1) null,
	constraint fk_users_user_roles1
		foreign key (user_role_id) references user_roles (id)
);

create table doctor_info
(
	doctor_id int not null
		primary key,
	specialization varchar(45) not null,
	work_experience int not null,
	classification int not null,
	price double not null,
	constraint fk_DoctorInfo_users1
		foreign key (doctor_id) references users (id)
);

create index fk_DoctorInfo_users1_idx
	on doctor_info (doctor_id);

create table patient_cards
(
	id int auto_increment
		primary key,
	user_id int not null,
	spare_number varchar(45) not null,
	age int not null,
	sex varchar(45) not null,
	constraint fk_outpatient_card_users1
		foreign key (user_id) references users (id)
);

create table consultations
(
    id                  int auto_increment
        primary key,
    doctor_id           int                                                   not null,
    patient_id          int                                                   not null,
    treatment_course_id int                                                   null,
    communication_type  enum ('ONLINE', 'FACE_TO_FACE')                       not null,
    date                timestamp                                             not null,
    duration            int                                                   null,
    price               double                                                null,
    status              enum ('APPROVED', 'PENDING', 'COMPLETED', 'REJECTED') null,
    parent_id           int                                                   null,
    child_id            int                                                   null,
    constraint fk_consultation_outpatient_card1
        foreign key (patient_id) references patient_cards (id),
    constraint fk_consultations_treatment_courses1
        foreign key (treatment_course_id) references treatment_courses (id),
    constraint fk_consultations_users1
        foreign key (doctor_id) references users (id)
);

create index fk_consultation_outpatient_card1_idx
    on consultations (patient_id);

create index fk_consultations_treatment_courses1_idx
    on consultations (treatment_course_id);

create index fk_consultations_users1_idx
    on consultations (doctor_id);


create table hospitalizations
(
	id int auto_increment
		primary key,
	patient_id int not null,
	status enum('PENDING', 'COMPLETED', 'REJECTED', 'APPROVED') null,
	doctor_id int not null,
	constraint doctor_id
		foreign key (doctor_id) references users (id),
	constraint fk_hospitalization_outpatient_card1
		foreign key (patient_id) references patient_cards (id)
);

create table chamber_staying
(
	hospitalization_id int not null,
	chamber_id int not null,
	hospitalization_date date not null,
	discharge_date date null,
	price double null,
	constraint fk_hospitalization_has_chambers_chambers1
		foreign key (chamber_id) references chambers (id),
	constraint fk_hospitalization_has_chambers_hospitalization1
		foreign key (hospitalization_id) references hospitalizations (id)
);

create index fk_hospitalization_has_chambers_chambers1_idx
	on chamber_staying (chamber_id);

create index fk_hospitalization_has_chambers_hospitalization1_idx
	on chamber_staying (hospitalization_id);

create index fk_hospitalization_outpatient_card1_idx
	on hospitalizations (patient_id);

create index fk_outpatient_card_users1_idx
	on patient_cards (user_id);

create index fk_users_user_roles1_idx
	on users (user_role_id);

