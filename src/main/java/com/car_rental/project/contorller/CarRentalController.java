package com.car_rental.project.contorller;

import com.car_rental.project.com.car_rental.project.service.CarRentalService;
import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/carrental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;
    
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserveCar(@RequestParam CarType carType,
                                                 @RequestParam String startDate,
                                                 @RequestParam int numberOfDays){
        LocalDate date = LocalDate.parse(startDate);
        Reservation reservation = carRentalService.reserveCar(carType, date, numberOfDays);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("/return/{reservationId}")
    public ResponseEntity<Car> returnCar(@PathVariable Long reservationId){
        Car car = carRentalService.returnCar(reservationId);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

}
