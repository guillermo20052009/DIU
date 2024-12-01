package catalogo.modelo;

public class ExcepcionArticulo extends RuntimeException {
    public ExcepcionArticulo(String message) {
        super(message);
    }
}
