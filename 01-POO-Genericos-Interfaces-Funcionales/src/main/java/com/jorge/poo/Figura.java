package com.jorge.poo;

public abstract class Figura {

    public abstract double area();

    public void describir() {
        System.out.printf("%s tiene un area de %.2f%n", getClass().getSimpleName(), area());
    }
}
