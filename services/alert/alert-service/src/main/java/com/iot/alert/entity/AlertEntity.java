package com.iot.alert.entity;

import com.iot.alert.enums.AlertType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="alerts")
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "sensor_id" ,nullable=false)
    private String sensorId;

    @Enumerated(EnumType.STRING)
    @Column(name= "alert_type", nullable = false)
    private AlertType alertType;

    private Double value;

    private Double threshold;

    private String message;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }
}


