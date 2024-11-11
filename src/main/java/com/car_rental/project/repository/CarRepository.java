package com.car_rental.project.repository;

import com.car_rental.project.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository  extends JpaRepository<Car, Long> {

    Optional<Car> findFirstByTypeAndIsAvailable(Car car, boolean isAvailable);
}
