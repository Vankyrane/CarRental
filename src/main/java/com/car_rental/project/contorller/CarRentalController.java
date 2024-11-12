package com.car_rental.project.contorller;

import com.car_rental.project.com.car_rental.project.service.CarRentalService;
import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/carrental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;
    
    @PostMapping("/reserve")
    public Reservation reserveCar(@RequestParam CarType carType,
                                  @RequestParam String startDate,
                                  @RequestParam int numberOfDays){
        LocalDate date = LocalDate.parse(startDate);
        return carRentalService.reserveCar(carType, date, numberOfDays);
    }

    @PostMapping("/return/{reservationId}")
    public Car returnCar(@PathVariable Long reservationId){
        return carRentalService.returnCar(reservationId);
    }

}
