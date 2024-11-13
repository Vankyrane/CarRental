package com.car_rental.project.com.car_rental.project.service;

import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.repository.CarRepository;
import com.car_rental.project.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation reserveCar(CarType carType, LocalDate startDate, int numberOfDays){
        Optional<Car> availableCar = carRepository.findFirstByTypeAndIsAvailable(carType, true);

        if(availableCar.isPresent()){
            Car car = availableCar.get();
            car.setAvailable(false);
            carRepository.save(car);

            Reservation reservation = new Reservation();
            reservation.setCarType(carType);
            reservation.setCar(car);
            reservation.setStartDate(startDate);
            reservation.setEndDate(startDate.plusDays(numberOfDays));

            return reservationRepository.save(reservation);
        }else{
            throw  new RuntimeException("No cars available for the selected car type");
        }
    }

    @Override
    public Car returnCar(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not Found"));

        Car car = reservation.getCar();
        car.setAvailable(true);
        carRepository.save(car);
        return car;
    }
}
