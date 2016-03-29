package cz.muni.fi.pv168;

/**
 * Created by PavelKotala on 28.3.2016.
 */
public class IllegalEntityException extends RuntimeException {
    public IllegalEntityException() {
    }

    public IllegalEntityException(String message) {
        super(message);
    }

    public IllegalEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEntityException(Throwable cause) {
        super(cause);
    }
}
