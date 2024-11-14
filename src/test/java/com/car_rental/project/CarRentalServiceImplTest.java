package com.car_rental.project;

import com.car_rental.project.com.car_rental.project.service.CarRentalServiceImpl;
import com.car_rental.project.exception.APIException;
import com.car_rental.project.exception.ResourceNotFoundException;
import com.car_rental.project.model.Car;
import com.car_rental.project.model.CarType;
import com.car_rental.project.model.Reservation;
import com.car_rental.project.payload.CarDTO;
import com.car_rental.project.repository.CarRepository;
import com.car_rental.project.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRentalServiceImplTest {

    @InjectMocks
    private CarRentalServiceImpl carRentalService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReserveCar_Success() {
        CarType carType = CarType.SEDAN;
        LocalDate startDate = LocalDate.now();
        int numberOfDays = 5;
        Car availableCar = new Car();
        availableCar.setType(carType);
        availableCar.setAvailable(true);

        when(carRepository.findFirstByTypeAndIsAvailable(carType, true)).thenReturn(availableCar);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reservation reservation = carRentalService.reserveCar(carType, startDate, numberOfDays);

        assertNotNull(reservation);
        assertEquals(carType, reservation.getCarType());
        assertEquals(startDate, reservation.getStartDate());
        assertEquals(startDate.plusDays(numberOfDays), reservation.getEndDate());
        verify(carRepository, times(1)).save(availableCar);
    }

    @Test
    void testReserveCar_CarNotFound() {
        CarType carType = CarType.SEDAN;
        LocalDate startDate = LocalDate.now();

        when(carRepository.findFirstByTypeAndIsAvailable(carType, true)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> carRentalService.reserveCar(carType, startDate, 5));
    }

    @Test
    void testAddCar_Success() {
        CarDTO carDTO = new CarDTO();
        carDTO.setType(CarType.SUV);
        carDTO.setAvailable(true);

        Car car = new Car();
        car.setType(CarType.SUV);
        car.setAvailable(true);

        when(modelMapper.map(carDTO, Car.class)).thenReturn(car);
        when(carRepository.findFirstByTypeAndIsAvailable(carDTO.getType(), carDTO.isAvailable())).thenReturn(null);
        when(carRepository.save(car)).thenReturn(car);
        when(modelMapper.map(car, CarDTO.class)).thenReturn(carDTO);

        CarDTO savedCarDTO = carRentalService.addCar(carDTO);

        assertNotNull(savedCarDTO);
        assertEquals(carDTO.getType(), savedCarDTO.getType());
    }

    @Test
    void testAddCar_CarAlreadyExists() {
        CarDTO carDTO = new CarDTO();
        carDTO.setType(CarType.SUV);
        carDTO.setAvailable(true);

        Car carFromDB = new Car();
        carFromDB.setType(CarType.SUV);
        carFromDB.setAvailable(true);

        when(modelMapper.map(carDTO, Car.class)).thenReturn(carFromDB);
        when(carRepository.findFirstByTypeAndIsAvailable(carDTO.getType(), carDTO.isAvailable())).thenReturn(carFromDB);

        assertThrows(APIException.class, () -> carRentalService.addCar(carDTO));
    }

    @Test
    void testGetAllCars_Success() {
        Car car1 = new Car();
        car1.setType(CarType.SEDAN);
        car1.setAvailable(true);
        Car car2 = new Car();
        car2.setType(CarType.SUV);
        car2.setAvailable(false);

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));
        when(modelMapper.map(car1, CarDTO.class)).thenReturn(new CarDTO());
        when(modelMapper.map(car2, CarDTO.class)).thenReturn(new CarDTO());

        List<CarDTO> cars = carRentalService.getAllCars();

        assertNotNull(cars);
        assertEquals(2, cars.size());
    }

    @Test
    void testGetAllCars_NoCarsAvailable() {
        when(carRepository.findAll()).thenReturn(List.of());

        assertThrows(APIException.class, () -> carRentalService.getAllCars());
    }

    @Test
    void testReturnCar_Success() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        Car car = new Car();
        car.setAvailable(false);
        reservation.setCar(car);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(modelMapper.map(car, CarDTO.class)).thenReturn(new CarDTO());

        CarDTO returnedCarDTO = carRentalService.returnCar(reservationId);

        assertNotNull(returnedCarDTO);
        assertTrue(car.isAvailable());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testReturnCar_ReservationNotFound() {
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> carRentalService.returnCar(reservationId));
    }
}
