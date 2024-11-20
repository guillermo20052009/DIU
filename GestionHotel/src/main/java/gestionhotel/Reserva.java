package gestionhotel;

import gestionhotel.modelo.Regimen;
import gestionhotel.modelo.tipo_habitacion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

// Esta clase se encarga de manejar las reservas dentro de la aplicación, no es la clase que vamos a pasar a la base de
// datos, ya que vamos a utilizar datos más sofisticados
// tendrá dos constructores, que se usarán más adelante, getters, setters y ToString

public class Reserva {
    private IntegerProperty idReserva;
    private java.sql.Date fechaLlegada;
    private java.sql.Date fechaSalida;
    private tipo_habitacion tipoHabitacion;
    private boolean fumador;
    private Regimen regimen;
    private IntegerProperty numero_habitaciones;
    private StringProperty dni_cliente;

    public Reserva(int idreserva,java.sql.Date fechaLlegada, java.sql.Date fechaSalida, tipo_habitacion tipoHabitacion,
                   boolean fumador, Regimen regimen, int numero_habitaciones, String dni_cliente) {
        this.idReserva=new SimpleIntegerProperty(idreserva);
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.tipoHabitacion = tipoHabitacion;
        this.fumador = fumador;
        this.regimen = regimen;
        this.numero_habitaciones = new SimpleIntegerProperty(numero_habitaciones);  // IntegerProperty
        this.dni_cliente = new SimpleStringProperty(dni_cliente);  // StringProperty
    }

    public Reserva(Date fechaLlegada, Date fechaSalida, tipo_habitacion tipoHabitacion, boolean fumador, Regimen regimen, Integer numero_habitaciones, String dni_cliente) {
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.tipoHabitacion = tipoHabitacion;
        this.fumador = fumador;
        this.regimen = regimen;
        this.numero_habitaciones = new SimpleIntegerProperty(numero_habitaciones);
        this.dni_cliente =  new SimpleStringProperty(dni_cliente);
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public tipo_habitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(tipo_habitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public boolean isFumador() {
        return fumador;
    }

    public void setFumador(boolean fumador) {
        this.fumador = fumador;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    public int getNumero_habitaciones() {
        return numero_habitaciones.get();
    }

    public IntegerProperty numero_habitacionesProperty() {
        return numero_habitaciones;
    }

    public void setNumero_habitaciones(int numero_habitaciones) {
        this.numero_habitaciones.set(numero_habitaciones);
    }

    public String getDni_cliente() {
        return dni_cliente.get();
    }

    public StringProperty dni_clienteProperty() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente.set(dni_cliente);
    }

    public int getIdReserva() {
        return idReserva.get();
    }

    public IntegerProperty idReservaProperty() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva.set(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "fechaLlegada=" + fechaLlegada +
                ", fechaSalida=" + fechaSalida +
                ", tipoHabitacion=" + tipoHabitacion +
                ", fumador=" + fumador +
                ", regimen=" + regimen +
                ", numero_habitaciones=" + numero_habitaciones.get() +
                ", dni_cliente=" + dni_cliente.get() +
                '}';
    }
}
