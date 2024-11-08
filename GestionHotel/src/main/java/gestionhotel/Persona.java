package gestionhotel;

import javafx.beans.property.SimpleStringProperty;

public class Persona {
    private SimpleStringProperty DNI;
    private SimpleStringProperty Nombre;
    private SimpleStringProperty Apellidos;
    private SimpleStringProperty direccion;
    private SimpleStringProperty localidad;
    private SimpleStringProperty provincia;

    public Persona(String nombre, String DNI, String apellidos, String direccion, String localidad, String provincia) {
        this.Nombre = new SimpleStringProperty(nombre);
        this.DNI = new SimpleStringProperty(DNI);
        this.Apellidos = new SimpleStringProperty(apellidos);
        this.direccion = new SimpleStringProperty(direccion);
        this.localidad = new SimpleStringProperty(localidad);
        this.provincia = new SimpleStringProperty(provincia);
    }

    public String getDNI() {
        return DNI.get();
    }

    public SimpleStringProperty DNIProperty() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI.set(DNI);
    }

    public String getNombre() {
        return Nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre.set(nombre);
    }

    public String getApellidos() {
        return Apellidos.get();
    }

    public SimpleStringProperty apellidosProperty() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        this.Apellidos.set(apellidos);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public SimpleStringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getLocalidad() {
        return localidad.get();
    }

    public SimpleStringProperty localidadProperty() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad.set(localidad);
    }

    public String getProvincia() {
        return provincia.get();
    }

    public SimpleStringProperty provinciaProperty() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia.set(provincia);
    }
}
