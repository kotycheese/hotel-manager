package cz.muni.fi.pv168;

/**
 * Created by PavelKotala on 19.3.2016.
 */
public class ServiceFailureException extends RuntimeException {
    public ServiceFailureException() {
    }

    public ServiceFailureException(String message) {
        super(message);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceFailureException(Throwable cause) {
        super(cause);
    }
}
