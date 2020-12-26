package ru.homework.framework.pages;

import java.util.ArrayList;
import java.util.List;

public class Product {
    String name;

    int price;

    public static List<Product> listOfProducts = new ArrayList<>();

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
