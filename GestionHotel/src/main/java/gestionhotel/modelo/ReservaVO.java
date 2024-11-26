package gestionhotel.modelo;

import java.sql.Date;

//Clase interna para recoger de la base de datos la reserva, no será utilizado internamente

public class ReservaVO {
    private int idReserva;
    private java.sql.Date fechaLlegada;
    private java.sql.Date fechaSalida;
    private String tipo_habitacion;
    private boolean fumador;
    private String regimen;
    private int numero_habitaciones;
    private String dni_cliente;

    public ReservaVO(int idReserva, int numero_habitaciones, String regimen, boolean fumador, String tipo_habitacion, Date fechaSalida, Date fechaLlegada, String dni_cliente) {
        this.dni_cliente = dni_cliente;
        this.numero_habitaciones = numero_habitaciones;
        this.regimen = regimen;
        this.fumador = fumador;
        this.tipo_habitacion = tipo_habitacion;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
        this.idReserva = idReserva;
    }
    public ReservaVO(int numero_habitaciones, String regimen, boolean fumador, String tipo_habitacion, Date fechaSalida, Date fechaLlegada, String dni_cliente) {
        this.dni_cliente = dni_cliente;
        this.numero_habitaciones = numero_habitaciones;
        this.regimen = regimen;
        this.fumador = fumador;
        this.tipo_habitacion = tipo_habitacion;
        this.fechaSalida = fechaSalida;
        this.fechaLlegada = fechaLlegada;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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

    public String getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(String tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public boolean isFumador() {
        return fumador;
    }

    public void setFumador(boolean fumador) {
        this.fumador = fumador;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public int getNumero_habitaciones() {
        return numero_habitaciones;
    }

    public void setNumero_habitaciones(int numero_habitaciones) {
        this.numero_habitaciones = numero_habitaciones;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    @Override
    public String toString() {
        return "ReservaVO{" +
                "idReserva=" + idReserva +
                ", fechaLlegada=" + fechaLlegada +
                ", fechaSalida=" + fechaSalida +
                ", tipo_habitacion='" + tipo_habitacion + '\'' +
                ", fumador=" + fumador +
                ", regimen='" + regimen + '\'' +
                ", numero_habitaciones=" + numero_habitaciones +
                ", dni_cliente='" + dni_cliente + '\'' +
                '}';
    }
}
