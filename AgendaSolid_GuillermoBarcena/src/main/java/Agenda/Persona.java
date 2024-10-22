package Agenda;

import javafx.beans.property.StringProperty;

public class Persona {
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty direccion;
    private StringProperty ciudad;
    private StringProperty codigoPostal;
    private StringProperty fechaNacimiento;

    public Persona(StringProperty nombre, StringProperty apellido, StringProperty direccion, StringProperty ciudad, StringProperty codigoPostal, StringProperty fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getCiudad() {
        return ciudad.get();
    }

    public StringProperty ciudadProperty() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }

    public String getCodigoPostal() {
        return codigoPostal.get();
    }

    public StringProperty codigoPostalProperty() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal.set(codigoPostal);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public StringProperty fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre=" + nombre.get() +
                ", apellido=" + apellido.get() +
                ", direccion=" + direccion.get() +
                ", ciudad=" + ciudad.get() +
                ", codigoPostal=" + codigoPostal.get() +
                ", fechaNacimiento=" + fechaNacimiento.get() +
                '}';
    }
}
