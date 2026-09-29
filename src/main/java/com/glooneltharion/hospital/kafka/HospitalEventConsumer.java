package com.glooneltharion.hospital.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HospitalEventConsumer {

    public HospitalEventConsumer() {

        System.out.println("#################################");
        System.out.println("HOSPITAL EVENT CONSUMER STARTED");
        System.out.println("#################################");
    }

    @KafkaListener(
            topics = KafkaConfig.APPOINTMENT_CREATED_TOPIC,
            groupId = "hospital-consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeAppointmentCreated(
            AppointmentCreatedEvent event
    ) {

        System.out.println("=================================");
        System.out.println("APPOINTMENT CREATED EVENT");
        System.out.println("Appointment ID: " + event.getAppointmentId());
        System.out.println("Patient ID: " + event.getPatientId());
        System.out.println("Doctor ID: " + event.getDoctorId());
        System.out.println("Appointment date: " + event.getAppointmentDate());
        System.out.println("=================================");
    }
}
