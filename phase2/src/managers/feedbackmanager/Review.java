package managers.feedbackmanager;

import java.io.Serializable;
/**
 * An instance of this class represents the review from a user
 * @author Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class Review implements Serializable {
    private int receiverId;
    private int reviewerId;
    private int point;
    private String reason;

    /** Constructs a review instance.
     * @param receiverId the id of user who is reviewed
     * @param reviewerId the id of user who reviews others
     * @param point the point the reviewer give to the receiver
     * @param reason the reason why review
     */
    public Review(int receiverId, int reviewerId, int point, String reason){
        this.receiverId = receiverId;
        this.reviewerId = reviewerId;
        this.point = point;
        this.reason = reason;
    }

    /** get the receiver id
     * @return the receiver id
     */
    protected int getReceiverId(){
        return receiverId;
    }

    /** get the reviewer id
     * @return the reviewer id
     */
    protected int getReviewerId(){
        return reviewerId;
    }

    /** get the point
     * @return the point of the review
     */
    protected int getPoint(){
        return point;
    }

    /** get a string describe the review
     * @return a string for a review
     */
    public String toString(){
        return "User id " + receiverId + " has been reviewed by user id " + reviewerId + " with point: "+ point +
                ".\nReview message: "+ reason;
    }
}
