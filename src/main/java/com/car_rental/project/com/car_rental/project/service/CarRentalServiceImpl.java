package com.car_rental.project.com.car_rental.project.service;

import com.car_rental.project.exception.APIException;
import com.car_rental.project.exception.ResourceNotFoundException;
import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;
import com.car_rental.project.repository.CarRepository;
import com.car_rental.project.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public Reservation reserveCar(CarType carType, LocalDate startDate, int numberOfDays){
        Car availableCar = carRepository.findFirstByTypeAndIsAvailable(carType, true);

        if(availableCar != null){
            Car car = availableCar;
            car.setAvailable(false);
            carRepository.save(car);

            Reservation reservation = new Reservation();
            reservation.setCarType(carType);
            reservation.setCar(car);
            reservation.setStartDate(startDate);
            reservation.setEndDate(startDate.plusDays(numberOfDays));
            return reservationRepository.save(reservation);
        }else{
            throw  new ResourceNotFoundException("Car", "carType", String.valueOf(carType));
        }
    }

    @Override
   public CarDTO addCar(CarDTO carDTO){
        Car car = modelMapper.map(carDTO,Car.class);
        Car carFromDB = carRepository.findFirstByTypeAndIsAvailable(carDTO.getType(), carDTO.isAvailable());
        if(carFromDB != null){
            throw new APIException("Car with Type "+  car.getType() + " aleardy Exist");
        }
        Car savedCar = carRepository.save(car);
        return modelMapper.map(savedCar, CarDTO.class);
   }

   @Override
   public List<CarDTO> getAllCars(){
        List<Car> carList = carRepository.findAll();
        if(carList.isEmpty()){
            throw new APIException("There are no Cars in Garage till now");
        }
        List<CarDTO> carDTOList = carList.stream().map(car -> modelMapper.map(car,CarDTO.class)).toList();
        return carDTOList;
    }

    @Override
    public CarDTO returnCar(Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "reservationId", reservationId));

        Car car = reservation.getCar();
        car.setAvailable(true);
        carRepository.save(car);
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        return carDTO;
    }
}
