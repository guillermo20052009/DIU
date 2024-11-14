package gestionhotel.modelo.repository;

public class ExcepcionReserva extends RuntimeException {
    private String mensaje;

    public ExcepcionReserva() {}
    public ExcepcionReserva(String message) {this.mensaje = message;}
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
