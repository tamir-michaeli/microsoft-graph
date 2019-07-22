package objects;

public class MissingParameter extends RuntimeException {
    public MissingParameter(String msg) {
        super (msg);
    }
}
