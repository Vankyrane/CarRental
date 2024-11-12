package com.car_rental.project.contorller;

import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/carrental")
public class CarRentalController {

    @Autowired
    private Reservation reservation;

    @PostMapping("/reserve")
    public Reservation reserveCar(@RequestParam CarType carType,
                                  @RequestParam String startDate,
                                  @RequestParam int numberOfDays){
        LocalDate date = LocalDate.parse(startDate);
        return reservation;
    }

    @PostMapping("/return/{reservationId}")
    public String returnCar(@PathVariable Long reservationId){
        return "Car returned successfully";
    }

}
