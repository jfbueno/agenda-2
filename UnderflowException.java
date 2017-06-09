package agenda;

public class UnderflowException extends RuntimeException {
    public UnderflowException() {
        super();
    }

    public UnderflowException(String msg){
        super(msg);
    }
}
