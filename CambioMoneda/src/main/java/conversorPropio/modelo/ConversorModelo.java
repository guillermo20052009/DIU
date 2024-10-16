package conversorPropio.modelo;

public class ConversorModelo {
    private String multiplicador;
    private String nombre;

    public ConversorModelo(String multiplicador, String nombre) {
        this.multiplicador = multiplicador;
        this.nombre = nombre;
    }
    public String getmultiplicador() {
        return multiplicador;
    }
    public String getNombre() {
        return nombre;
    }


}
