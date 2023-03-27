package hello.jdbc.repository.ex;

public class MyDbRuntimeException extends RuntimeException {

    public MyDbRuntimeException() {
        super();
    }

    public MyDbRuntimeException(String message) {
        super(message);
    }

    public MyDbRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbRuntimeException(Throwable cause) {
        super(cause);
    }
}
