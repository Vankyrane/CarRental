package com.car_rental.project.com.car_rental.project.service;

import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;
import com.car_rental.project.repository.CarRepository;
import com.car_rental.project.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRentalService {

    Reservation reserveCar(CarType carType, LocalDate startDate, int numberOfDays);

    Car addCar(Car car);

    List<Car> getAllCars();

    Car returnCar(Long reservationId);
}
