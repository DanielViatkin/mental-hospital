package com.epam.hospital.constant.database;

public class Column {

    public static final String CHAMBER_STAYING_CHAMBER_ID = "chamber_id";
    public static final String CHAMBER_STAYING_HOSPITALIZATION_ID = "hospitalization_id";
    public static final String CHAMBER_STAYING_DATE_IN = "hospitalization_date";
    public static final String CHAMBER_STAYING_DATE_OUT = "discharge_date";
    public static final String CHAMBER_STAYING_PRICE = "price";

    public static final String CHAMBER_ID = "id";
    public static final String CHAMBER_CHAMBER_TYPE_ID = "chamber_type_id";
    public static final String CHAMBER_HOSPITAL_ID = "hospital_id";
    public static final String CHAMBER_FREE_BEDS = "num_free_beds";

    public static final String CHAMBER_TYPE_NUMBER_OF_AVAILABLE_ROOMS = "num_of_available_chambers";
    public static final String CHAMBERS_TYPE_ID = "id";
    public static final String CHAMBERS_TYPE_TITLE = "title";
    public static final String CHAMBERS_TYPE_NUMBER_OF_BEDS = "num_of_beds";
    public static final String CHAMBERS_TYPE_PRICE = "price";
    public static final String CHAMBERS_IMAGE_REF = "img_ref";

    public static final String CONSULTATION_ID = "id";
    public static final String CONSULTATION_TREATMENT_COURSE_ID = "treatment_course_id";
    public static final String CONSULTATION_COMMUNICATION_TYPE = "communication_type";
    public static final String CONSULTATION_DOCTOR_ID = "doctor_id";
    public static final String CONSULTATION_PATIENT_ID = "patient_id";
    public static final String CONSULTATION_DATE = "date";
    public static final String CONSULTATION_DURATION = "duration";
    public static final String CONSULTATION_PRICE = "price";
    public static final String CONSULTATION_STATUS = "status";
    public static final String CONSULTATION_PARENT_ID = "parent_id";
    public static final String CONSULTATION_CHILD_ID = "child_id";

    public static final String DISEASE_SYMPTOMS_COURSE_ID = "treatment_course_id";
    public static final String DISEASE_SYMPTOMS_DISEASE_ID = "diseases_id";
    public static final String DISEASE_SYMPTOMS_SYMPTOMS = "symptoms";

    public static final String DISEASES_ID = "id";
    public static final String DISEASES_NAME = "name";
    public static final String DISEASES_DESCRIPTION = "description";

    public static final String DOCTOR_INFO_ID = "doctor_id";
    public static final String DOCTOR_INFO_SPECIALIZATION = "specialization";
    public static final String DOCTOR_INFO_WORK_EXPERIENCE = "work_experience";
    public static final String DOCTOR_INFO_CLASSIFICATION = "classification";
    public static final String DOCTOR_INFO_PRICE = "price";

    public static final String DRUG_RECIPES_TREATMENT_COURSE_ID = "treatment_course_id";
    public static final String DRUG_RECIPES_DRUG_ID = "drug_id";
    public static final String DRUG_RECIPES_DESCRIPTION = "description";
    public static final String DRUG_RECIPES_DOSE = "dose";

    public static final String DRUGS_ID = "id";
    public static final String DRUGS_NAME = "name";

    public static final String HOSPITALIZATION_ID = "id";
    public static final String HOSPITALIZATION_PATIENT_ID = "patient_id";
    public static final String HOSPITALIZATION_STATUS = "status";
    public static final String HOSPITALIZATION_DOCTOR_ID = "doctor_id";


    public static final String HOSPITAL_ID = "id";
    public static final String HOSPITAL_ADDRESS = "address";
    public static final String HOSPITAL_NUMBER = "number";

    public static final String PATIENT_CARD_ID = "id";
    public static final String PATIENT_CARD_USER_ID = "user_id";
    public static final String PATIENT_CARD_SPARE_NUMBER = "spare_number";
    public static final String PATIENT_CARD_AGE = "age";
    public static final String PATIENT_CARD_SEX = "sex";

    public static final String TREATMENT_COURSE_INSTRUCTION = "instructions";
    public static final String TREATMENT_COURSE_ID = "id";

    public static final String USER_ROLES_ID = "id";
    public static final String USER_ROLES_NAME = "name";

    public static final String USER_ID = "id";
    public static final String USER_ROLE_ID = "user_role_id";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_FIRS_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_NUMBER = "number";
    public static final String USER_IS_BANNED = "is_banned";

    public static final String ID = "id";

}
