package com.rental.luca.car;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping("/all")
    public ResponseEntity<List<Car>> getCars() {
        List<Car> cars = service.getCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") String id) {
        Car car = service.getCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Car>> filterCars(@RequestParam Map<String, String> filters) {
        List<Car> cars = service.filterCars(filters);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car newCar) {
        Car car = service.addCar(newCar);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Car> updateCar(@RequestBody Car newCar) {
        Car car = service.updateCar(newCar);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Car> deleteCar(@PathVariable("id") String id) {
        service.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
