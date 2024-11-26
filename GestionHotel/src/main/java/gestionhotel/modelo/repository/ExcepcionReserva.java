package gestionhotel.modelo.repository;

// Manejo de las excepciones para Reserva
public class ExcepcionReserva extends RuntimeException {
    private String mensaje;

    public ExcepcionReserva() {}
    public ExcepcionReserva(String message) {this.mensaje = message;}
    public String imprimirMensaje() {
        return this.mensaje;
    }
}
