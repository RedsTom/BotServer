package org.redstom.botserver.java.exceptions;

public class PluginAlreadyExistsException extends Exception {

    public PluginAlreadyExistsException() {
        super();
    }

    public PluginAlreadyExistsException(String message) {
        super(message);
    }

    public PluginAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected PluginAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
