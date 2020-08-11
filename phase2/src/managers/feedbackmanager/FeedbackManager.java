package managers.feedbackmanager;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represents the feedback from users
 * @author Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class FeedbackManager implements Serializable {
    private ArrayList<Report> listReport;
    private ArrayList<Review> listReview;

    /**
     * construct for new FeedbackManager instance
     */
    public FeedbackManager(){
        listReport = new ArrayList<>();
        listReview = new ArrayList<>();
    }

    /** add the new review with the given user ids, the point and the reason.
     * @param userId1 the id of the user who is been reviewed
     * @param userId2 the id of the user who review the other
     * @param point the point people gave to the user
     * @return true if the review is added
     */
    // the userId1 is the id of the user who is reviewed
    public boolean setReview(int userId1, int userId2, int point, String reason ){
        if(haveReview(userId1, userId2)|| userId1 == userId2){
            return false;}
        else{
           Review review = new Review(userId1, userId2, point, reason);
           listReview.add(review);
           return true;
        }
    }

    /** calculate the rate for a given user id
     * @param userId the id of the user
     * @return a double which is the average rate of the user, return -1.0 if the user has not been reviewed
     */
    public double calculateRate(int userId){
        if(!haveReview(userId)){
            return -1.0;
        }else{
            double sum = 0.0;
            int count = 0;
        for(Review review: listReview) {
            if (review.getReceiverId() == userId) {
                sum += review.getPoint();
                count += 1;
            } }
            return sum / count; }
    }

    /** add new report to the list of report
     * @param userId1 the id of user who has been reported
     * @param userId2 the id of user who report the user with userId1
     * @param reason the reason to report the user
     * @return true if the report is added to the list successful.
     */
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean updateReport(int userId1, int userId2, String reason){
        if (haveReport(userId1, userId2) || userId1 == userId2){
            return false;
        }else {
            Report report = new Report(userId1, userId2, reason);
            listReport.add(report);
            return true;
        }
    }

    /** get a list of reviews that review the given user
     * @param userId the id of user who has been reviewed
     * @return a list of reviews that reviews the user with userId
     */
    public ArrayList<Review> getReviewById(int userId){
        ArrayList<Review> reviews = new ArrayList<>();
        for(Review review: listReview){
            if (review.getReceiverId() == userId){
                reviews.add(review);
            }
        }return reviews;}

    /** delete a report from the listReport
     * @param userId1 the id of user who has been reported
     * @param userId2 the id of user who report the other user
     * @return true if the report is removed from the list successful
     */
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean deleteReport(int userId1, int userId2){
        for(Report report: listReport){
            if (report.getReceiverId() == userId1 && report.getReporterId() == userId2){
                listReport.remove(report);
                return true;
            }}return false;
        }

    /** delete a review from the listReview
     * @param userId1 the id of user who has been reviewed
     * @param userId2 the id of user who reviewed the other user
     * @return true if the review is removed from the list successful
     */
    // userId1 is the id of the user who has been reviewed, the userId2 is the id of user who review the other user
    public boolean deleteReview(int userId1, int userId2){
        for(Review review: listReview){
            if (review.getReceiverId() == userId1 && review.getReviewerId()== userId2){
                listReview.remove(review);
                return true;
            }}return false;
    }


    /** check if a user has been reported by the other user
     * @param receiverId the id of user is been reported
     * @param reporterId the reporter id
     * @return true if has been reported
     */
    public boolean haveReport(int receiverId, int reporterId) {
        for (Report report : listReport) {
           if (report.getReceiverId() == receiverId && report.getReporterId() == reporterId){
               return true;
           }
        }return false;

    }
    /** check if a user has been reviewed by the other user
     * @param receiverId the id of user is been reviewed
     * @param reviewerId the reviewer id
     * @return true if receiverId has been reviewed
     */
    public boolean haveReview(int receiverId, int reviewerId) {
        for (Review review : listReview) {
            if(review.getReceiverId() == receiverId && review.getReviewerId() == reviewerId){
                return true;
            }
        }return false;

    }
    /** check if a user has been reviewed
     * @param receiverId the id of user is been reviewed
     * @return true if receiverId has been reviewed
     */
    public boolean haveReview(int receiverId) {
        for (Review review : listReview) {
            if(review.getReceiverId() == receiverId){
                return true;
            }
        }return false; }}