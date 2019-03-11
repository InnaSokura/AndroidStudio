package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class BeerExport {
    List<String> getBrands (String color)
    {
        List<String> brands = new ArrayList<>();
        if (color.equals("light"))
        {
            brands.add("Jail Pale Ale ");
            brands.add("Gout Stout");
        }
        else if (color.equals("amber"))
        {
            brands.add("Jack Amber ");
            brands.add("ReMoose");
        }
        else if (color.equals("brown"))
        {
            brands.add("Jack Daniel ");
            brands.add("Botinish");
        }
        else if (color.equals("dark"))
        {
            brands.add("Dark beer ");
            brands.add("Nicole beer");
        }
        return brands;
    }
}
