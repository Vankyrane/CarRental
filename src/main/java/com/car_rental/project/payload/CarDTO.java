package com.car_rental.project.payload;

import com.car_rental.project.model.CarType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private CarType type;
    private boolean isAvailable = true;
}
