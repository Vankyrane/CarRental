package com.car_rental.project.contorller;

import com.car_rental.project.com.car_rental.project.service.CarRentalService;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;
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
    public ResponseEntity<CarDTO> addCar (@Valid @RequestBody CarDTO carDTO) {
        CarDTO carDTOFromService = carRentalService.addCar(carDTO);
        return new ResponseEntity<>(carDTOFromService, HttpStatus.OK) ;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> carDTOFromService = carRentalService.getAllCars();
        return new ResponseEntity<>(carDTOFromService, HttpStatus.OK);
    }
    
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> reserveCar(@RequestParam CarType carType,
                                                 @RequestParam String startDate,
                                                 @RequestParam int numberOfDays){
        LocalDate date = LocalDate.parse(startDate);
        Reservation reservation = carRentalService.reserveCar(carType, date, numberOfDays);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/return/{reservationId}")
    public ResponseEntity<CarDTO> returnCar(@PathVariable Long reservationId){
        CarDTO CarDTOFromService = carRentalService.returnCar(reservationId);
        return new ResponseEntity<>(CarDTOFromService, HttpStatus.OK);
    }

}
