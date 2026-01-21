package Repository_pack;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(String msg) {
        super(msg);
    }
}