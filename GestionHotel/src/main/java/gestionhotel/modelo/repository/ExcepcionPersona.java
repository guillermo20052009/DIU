package gestionhotel.modelo.repository;

// Manejo de las excepciones para Persona
public class ExcepcionPersona extends Exception {
    private String mensaje;

    public ExcepcionPersona() {}
    public ExcepcionPersona(String message) {this.mensaje = message;}
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
