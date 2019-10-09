package vlasovspringbanksystem.utils.exceptions;

public class NoEnoughMoneyException extends RuntimeException {
    public NoEnoughMoneyException() {
    }

    public NoEnoughMoneyException(String message) {
        super(message);
    }

    public NoEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEnoughMoneyException(Throwable cause) {
        super(cause);
    }

    public NoEnoughMoneyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
