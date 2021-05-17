package eu.redstom.botserver.plugins.loader.exceptions;

public class MissingAnnotationException extends Error {
    public MissingAnnotationException() {
        super();
    }

    public MissingAnnotationException(String message) {
        super(message);
    }

    public MissingAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingAnnotationException(Throwable cause) {
        super(cause);
    }

    protected MissingAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
