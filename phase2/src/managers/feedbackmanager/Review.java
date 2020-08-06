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
    public int getReceiverId(){
        return receiverId;
    }

    /** get the reviewer id
     * @return the reviewer id
     */
    public int getReviewerId(){
        return reviewerId;
    }

    /** get the point
     * @return the point of the review
     */
    public int getPoint(){
        return point;
    }

    /** get the reason
     * @return the reason for the review
     */
    public String getReason(){
        return reason;
    }

    /** set the point
     * @param point the point of the review
     */
    public void setPoint(int point){
        this.point = point;
    }

    /** set the reason
     * @param reason the reason for the review
     */
    public void setReason(String reason){
        this.reason =reason;
    }

    /** get a string describe the review
     * @return a string for a review
     */
    public String toString(){
        return "User has been reviewed: " + receiverId +", reviewer: "+ reviewerId + ", point: "+ point +
                ", reason: "+ reason;
    }
}
