package com.iot.dataprocessing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="sensor_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="sensor_id", nullable=false)
    private String sensorId;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name ="humidity")
    private Double humidity;

    @Column(name="timestamp")
    private String timestamp;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    //Kayıt edilmeden önce çalışır (createdAt doldur)
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

