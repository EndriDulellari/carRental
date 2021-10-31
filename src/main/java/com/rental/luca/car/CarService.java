package com.rental.luca.car;

import com.rental.luca.exception.RecordNotFoundException;
import com.rental.luca.generic.GenericService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CarService extends GenericService {

    private final CarRepository repository;
    public MongoTemplate mongoTemplate;

    public List<Car> getCars() {
        return repository.findAll();
    }


    public Car getCarById(String id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Car by Id " + id + " not found!"));
    }

    public List<Car> filterCars(Map<String, String> filters) {
        Query query = generateQuery(filters);
        return mongoTemplate.find(query, Car.class);
    }

    public Car addCar(Car car) {
        return repository.save(car);
    }

    public Car updateCar(Car car) {
        return repository.save(car);
    }

    public void deleteCar(String id) {
        repository.deleteById(id);
    }
}
