package managers.messagemanger;

import java.io.Serializable;

/**
 * An instance of this class represents a message  in this system.
 *
 * @author Hao Du
 * @version IntelliJ IDEA 2020.1
 */
public class Message implements Serializable{
    //basic
    private Integer senderId;
    private Integer receiverId;
    private String message;


    /** Constructor of item.
     * Set this senderId with senderId, set this receiverId with receiverId, set the message with message
     * @param senderId The id of the message receiver
     * @param receiverId The id of the message receiver
     * @param message The message itself
     */
    public Message(int senderId, int receiverId, String message){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    /**
     * Get the receiver's id.
     *
     * @return receiver's id.
     */

    protected Integer getReceiverId() {
        return receiverId;
    }

    /**
     * Override the to string to describe the item
     * @return A string representation for message
     */
    public String toString(){
        return "A message from user " + senderId + " to user " + receiverId + ": " + message + "\n";
    }
}



