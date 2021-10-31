package com.rental.luca.car;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
public class Car {

    @Id
    private String id;
    private String brand;
    private String model;
    private String color;
    private String plate;
    private double price;
    private List<Date> busyDays;
    private int extraKM;
    /**
     * Suggested
     **/
    private String fuel;
    private String transmission;
    private double engine;
    private int seats;
    private int doors;
    private int carProductionYear;
    private String city;
    private double starReview;
    private int totalReviews;
}
