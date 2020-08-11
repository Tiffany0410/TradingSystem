package managers.feedbackmanager;

import java.io.Serializable;
/**
 * An instance of this class represents the report from a user
 * @author Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class Report implements Serializable {
    private int receiverId;
    private int reporterId;
    private String reason;

    /** Constructs a report instance.
     * @param receiverId the id of user who is reported
     * @param reporterId the id of user who report others
     * @param reason the reason why report
     */
    public Report(int receiverId, int reporterId, String reason){
        this.receiverId = receiverId;
        this.reporterId = reporterId;
        this.reason = reason;
    }

    /** get the receiver id
     * @return the receiver id
     */
    protected int getReceiverId(){
        return receiverId;
    }

    /** get the reporter id
     * @return the reporter id
     */
    protected int getReporterId(){
        return reporterId;
    }

    /** get a string describe the report
     * @return a string for a report
     */
    public String toString(){
        return "User " + receiverId + " has been reported by user " + reporterId + ".\nReason: "+ reason;
    }
}
