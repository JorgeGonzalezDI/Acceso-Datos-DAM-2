package com.jorge.poo;

public class Circulo extends Figura {

    private final double radio;

    public Circulo(double radio) {
        this.radio = radio;
    }

    @Override
    public double area() {
        return Math.PI * radio * radio;
    }
}

