package com.iot.dataprocessing.repository;

import com.iot.dataprocessing.entity.SensorDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorDataEntity, Long> {
    // save(), findAll(), findById(), delete()..
}

