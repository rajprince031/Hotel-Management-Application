package exception;

public class NoItemsInListException extends RuntimeException {
    public NoItemsInListException(String message) {
      super(message);
    }
    public NoItemsInListException(){
    System.out.println("List is empty.");
    }
}
