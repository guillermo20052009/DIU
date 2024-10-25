package Agenda;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Persona {
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty direccion;
    private StringProperty ciudad;
    private StringProperty codigoPostal;
    private StringProperty fechaNacimiento;


        // Constructor que recibe Strings y convierte a StringProperty
        public Persona(String nombre, String apellido, String direccion, String ciudad, String codigoPostal, String fechaNacimiento) {
            this.nombre = new SimpleStringProperty(nombre);
            this.apellido = new SimpleStringProperty(apellido);
            this.direccion = new SimpleStringProperty(direccion);
            this.ciudad = new SimpleStringProperty(ciudad);
            this.codigoPostal = new SimpleStringProperty(codigoPostal);
            this.fechaNacimiento = new SimpleStringProperty(fechaNacimiento);
        }

        // Getters y setters para los StringProperty (opcional)
        public StringProperty nombreProperty() {
            return nombre;
        }

        public StringProperty apellidoProperty() {
            return apellido;
        }

        public StringProperty direccionProperty() {
            return direccion;
        }

        public StringProperty ciudadProperty() {
            return ciudad;
        }

        public StringProperty codigoPostalProperty() {
            return codigoPostal;
        }

        public StringProperty fechaNacimientoProperty() {
            return fechaNacimiento;
        }

    public String getNombre() {
        return nombre.get();
    }



    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }



    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getDireccion() {
        return direccion.get();
    }



    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getCiudad() {
        return ciudad.get();
    }



    public void setCiudad(String ciudad) {
        this.ciudad.set(ciudad);
    }

    public String getCodigoPostal() {
        return codigoPostal.get();
    }



    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal.set(codigoPostal);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.get();
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
