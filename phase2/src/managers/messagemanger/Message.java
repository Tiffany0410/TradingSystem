package managers.messagemanger;

import java.io.Serializable;

public class Message implements Serializable {
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

    public Integer getReceiverId() {
        return receiverId;
    }

    /**
     * Get the message.
     *
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Override the to string to describe the item
     * @return A string representation for message
     */
    public String toString(){
        return "A message from user " + senderId + " to user " + receiverId + ": " + message + "\n";
    }
}



