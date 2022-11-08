package com.example.ejercicio2repasoexamen1av.Modelos;

public class Producto {
    private String nombre;
    private float precio;
    private int cantiad;

    public Producto(String nombre, float precio, int cantiad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantiad = cantiad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantiad() {
        return cantiad;
    }

    public void setCantiad(int cantiad) {
        this.cantiad = cantiad;
    }
}
