package gestionhotel.modelo.repository;

public class ExcepcionPersona extends Exception {
    private String mensaje;

    public ExcepcionPersona() {}
    public ExcepcionPersona(String message) {this.mensaje = message;}
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
