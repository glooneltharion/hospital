package com.glooneltharion.hospital.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HospitalEventProducer {

    private final KafkaTemplate<String, AppointmentCreatedEvent> kafkaTemplate;

    public HospitalEventProducer(
            KafkaTemplate<String, AppointmentCreatedEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAppointmentCreatedEvent(
            AppointmentCreatedEvent event
    ) {
        kafkaTemplate.send(
                KafkaConfig.APPOINTMENT_CREATED_TOPIC,
                event.getAppointmentId().toString(),
                event
        );
    }
}
