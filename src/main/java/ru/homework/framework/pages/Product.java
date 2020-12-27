package ru.homework.framework.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Product {
    String name;

    String price;

    public static List<Product> listOfProducts = new ArrayList<>();


    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Название продукта: " + name
                + "\n                         цена: " + price + "\n";
    }

    public static List<String> getNames() {
        List<String> listOfNames = new ArrayList<>();
        for (Product product: listOfProducts) {
            listOfNames.add(product.name);
        }
        //System.out.println("В Продукте: \n" + listOfNames);
        return listOfNames;
    }
}
