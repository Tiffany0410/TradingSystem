package managers.messagemanger;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stores and manages all the Messages.
 * @author Shi Tang, Jianhong Guo
 * @version IntelliJ IDEA 2020.1.1
 */
public class MessageManager implements Serializable {
    private ArrayList<Message> listMessages;

    /**
     * Constructs a MessageManager
     */
    public MessageManager(){
        listMessages = new ArrayList<>();
    }

    /**
     * Return a list of messages that this user received
     * @param receiverId The receiver's id
     * @return a list of messages that this user received
     */
    public ArrayList<Message> getMessageFor(int receiverId){
        ArrayList<Message> messages = new ArrayList<>();
        for (Message message: listMessages){
            if (message.getReceiverId().equals(receiverId)){
                messages.add(message);
            }
        }
        return messages;
    }

    /**
     * To create a new message
     * @param senderId The sender's id
     * @param receiverId The receiver's id
     * @param message The message
     */
    public void createNewMessage(int senderId, int receiverId, String message){
        Message msg = new Message(senderId, receiverId, message);
        listMessages.add(msg);
    }
}
