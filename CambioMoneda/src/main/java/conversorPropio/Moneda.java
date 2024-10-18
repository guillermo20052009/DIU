package conversorPropio;

import eu.hansolo.toolbox.properties.FloatProperty;

public class Moneda {
    private Float multiplicador;
    private String nombre;

    public Moneda(Float multiplicador, String nombre) {
        this.multiplicador = multiplicador;
        this.nombre = nombre;
    }
    public Float getmultiplicador() {
        return multiplicador;
    }
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "ConversorModelo{" +
                "multiplicador=" + multiplicador +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
