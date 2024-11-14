package com.car_rental.project.com.car_rental.project.service;

import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;

import java.time.LocalDate;
import java.util.List;

public interface CarRentalService {

    Reservation reserveCar(CarType carType, LocalDate startDate, int numberOfDays);

    CarDTO addCar(CarDTO carDTO);

    List<CarDTO> getAllCars();

    CarDTO returnCar(Long reservationId);
}
