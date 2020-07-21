package exception;

/**
 * Invalid id exception
 * @author YuanzeBao
 * @version IntelliJ IDEA 2020.1
 */
public class InvalidIdException extends Exception {
    /**
     * @param msg message displayed
     */
    public InvalidIdException(String msg){
        super(msg);
    }
}
