package com.example.conversorexamen;

import javafx.beans.property.*;

public class Moneda {
    StringProperty nombre;
    IntegerProperty codigo;
    FloatProperty multiplicador;

    public Moneda(String nombre, int codigo, Float multiplicador) {
        this.nombre = new SimpleStringProperty(nombre);
        this.codigo = new SimpleIntegerProperty(codigo);
        this.multiplicador = new SimpleFloatProperty(multiplicador);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public int getCodigo() {
        return codigo.get();
    }

    public IntegerProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo.set(codigo);
    }

    public float getMultiplicador() {
        return multiplicador.get();
    }

    public FloatProperty multiplicadorProperty() {
        return multiplicador;
    }

    public void setMultiplicador(float multiplicador) {
        this.multiplicador.set(multiplicador);
    }

    @Override
    public String toString() {
        return "Moneda{" +
                "nombre=" + nombre.get() +
                ", codigo=" + codigo.get() +
                ", multiplicador=" + multiplicador.get() +
                '}';
    }
}


