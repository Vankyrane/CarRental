package com.car_rental.project.contorller;

import com.car_rental.project.com.car_rental.project.service.CarRentalService;
import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;
import com.car_rental.project.payload.CarReservationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/carrental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    @PostMapping("/addCar")
    public ResponseEntity<Car> addCar (@Valid @RequestBody Car car) {
        return new ResponseEntity<>(carRentalService.addCar(car), HttpStatus.OK) ;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carRentalService.getAllCars(), HttpStatus.OK);
    }
    
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
