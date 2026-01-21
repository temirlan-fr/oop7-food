package Exception_pack;

public class InvalidQuantity extends RuntimeException {
    public InvalidQuantity (String msg) {
        super(msg);
    }
}
