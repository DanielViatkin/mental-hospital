package com.epam.hospital.dao.builder;

import com.epam.hospital.dao.builder.impl.*;

public class BuilderFactory {
    private final static UserBuilder userBuilder = new UserBuilder();
    private final static ChamberBuilder chamberBuilder = new ChamberBuilder();
    private final static ChamberTypeBuilder chamberTypeBuilder = new ChamberTypeBuilder();
    private final static ConsultationBuilder consultationBuilder = new ConsultationBuilder();
    private final static HospitalizationBuilder hospitalizationBuilder = new HospitalizationBuilder();
    private final static PatientCardBuilder patientCardBuilder = new PatientCardBuilder();
    private final static TreatmentCourseBuilder treatmentCourseBuilder = new TreatmentCourseBuilder();
    private final static DrugRecipeBuilder drugRecipeBuilder = new DrugRecipeBuilder();
    private final static DiseaseSymptomBuilder diseaseSymptomBuilder = new DiseaseSymptomBuilder();
    private final static DrugBuilder drugBuilder = new DrugBuilder();
    private final static ChamberStayingBuilder chamberStayingBuilder = new ChamberStayingBuilder();
    private final static DiseaseBuilder diseaseBuilder = new DiseaseBuilder();
    private final static DoctorInfoBuilder doctorInfoBuilder = new DoctorInfoBuilder();

    public static UserBuilder getUserBuilder() {
        return userBuilder;
    }

    public static ChamberBuilder getChamberBuilder() {
        return chamberBuilder;
    }

    public static ChamberTypeBuilder getChamberTypeBuilder() {
        return chamberTypeBuilder;
    }

    public static ConsultationBuilder getConsultationBuilder() {
        return consultationBuilder;
    }

    public static HospitalizationBuilder getHospitalizationBuilder() {
        return hospitalizationBuilder;
    }

    public static PatientCardBuilder getPatientCardBuilder() {
        return patientCardBuilder;
    }

    public static TreatmentCourseBuilder getTreatmentCourseBuilder() {
        return treatmentCourseBuilder;
    }

    public static DrugRecipeBuilder getDrugRecipeBuilder() {
        return drugRecipeBuilder;
    }

    public static DiseaseSymptomBuilder getDiseaseSymptomBuilder() {
        return diseaseSymptomBuilder;
    }

    public static DrugBuilder getDrugBuilder() {
        return drugBuilder;
    }

    public static ChamberStayingBuilder getChamberStaying() {
        return chamberStayingBuilder;
    }

    public static DiseaseBuilder getDiseaseBuilder() {
        return diseaseBuilder;
    }

    public static DoctorInfoBuilder getDoctorInfoBuilder() {
        return doctorInfoBuilder;
    }
}
