package model;

public class DuplicateOrderIdException extends Exception{

    public DuplicateOrderIdException(String message) {
        super(message);
    }
}
