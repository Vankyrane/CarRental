package com.car_rental.project.payload;

import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarReservationDTO {
    private Long reservationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private CarType carType;
    private Car car;
}
