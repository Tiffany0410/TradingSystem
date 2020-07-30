package managers.feedbackmanager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class represents the feedback from users
 * @author Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class FeedbackManager {
    // the key of the outer map is id of user who has been reported, the key of the nested map is the id of the user
    // who report the user, and the value is the reason.
    private Map<Integer, Map<Integer, String>> reportMap;
    // the key of the outer map is the id of the user who is reviewed, the key of the nested map is the id of the user
    // who review the user. The value is the a list of string, and the first item is point, the second item is reason.
    private Map<Integer, Map<Integer, String[]>> reviewMap;

    /**
     * set reportMap and reviewMap to an empty map
     */
    public FeedbackManager(){
        reportMap = new HashMap<>();
        reviewMap = new HashMap<>();
    }

    /** getter for reportMap
     * @return the reportMap
     */
    public Map<Integer, Map<Integer, String>> getReportMap(){
        return reportMap;
    }

    /** getter for reviewMap
     * @return the reviewMap
     */
    public Map<Integer, Map<Integer, String[]>> getReviewMap(){
        return reviewMap;
    }

    /** add the new review with the given user ids, the point and the reason.
     * @param userId1 the id of the user who is been reviewed
     * @param userId2 the id of the user who review the other
     * @param point the point people gave to the user
     * @return true if the review is added
     */
    // the userId1 is the id of the user who is reviewed
    public boolean setReview(int userId1, int userId2, int point, String reason ){
        if(reviewMap.containsKey(userId1) && reviewMap.get(userId1).containsKey(userId2)){
            return false;}
        else if(reviewMap.containsKey(userId1)){
            String string = String.valueOf(point);
            String[] strings = new String[2];
            strings[0] = string;
            strings[1] = reason;
            reviewMap.get(userId1).put(userId2,strings);
        }else{
            String string = String.valueOf(point);
            String[] strings = new String[2];
            strings[0] = string;
            strings[1] = reason;
            HashMap<Integer, String[]> map1 = new HashMap<>();
            map1.put(userId2, strings);
            reviewMap.put(userId1,map1);
        }return true;
    }

    /** calculate the rate for a given user id
     * @param userId the id of the user
     * @return a double which is the average rate of the user, return -1 if the user id is not in the reviewMap
     */
    public double calculateRate(int userId){
        if (!reviewMap.containsKey(userId)){
            return -1.0;
        }else{
            Map<Integer, String[]> listReview = reviewMap.get(userId);
            int sum = 0;
            for(String[] strings: listReview.values()){
                int int1 = Integer.parseInt(strings[0]);
                sum += int1;
            }
            return (double) sum / reviewMap.get(userId).size(); }
    }

    /** add new report to the reportMap
     * @param userId1 the id of user who has been reported
     * @param userId2 the id of user who report the user with userId1
     * @param reason the reason to report the user
     * @return true if the report is added to the map successful.
     */
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean updateReport(int userId1, int userId2, String reason){
        if (reportMap.containsKey(userId1) && reportMap.get(userId1).containsKey(userId2)){
            return false;
        }else if(reportMap.containsKey(userId1)){
            reportMap.get(userId1).put(userId2,reason);
            return true;
        }else {
            HashMap<Integer, String> map1 = new HashMap<>() ;
            map1.put(userId2, reason);
            reportMap.put(userId1,map1);
            return true;
        }
    }
    /** get the map of user id and reason that report the given user
     * @param userId the id of user who has been reported
     * @return a map that key is the id of user who report the given id, and the value is the reason if the user is
     * in the report map. Otherwise, return a map with 0 as key, and empty string as a value
     */
    public Map<Integer, String> getReportById(int userId){
        if (reportMap.containsKey(userId)){
            return reportMap.get(userId);
        }else{
            HashMap<Integer, String> map1 = new HashMap<>();
            map1.put(0,"");
            return map1;
        }
    }
    /** get a map that key is the id of user who report the given id, and the value is the point and reason.
     * @param userId the id of user who has been reviewed
     * @return a map that key is the id of user who report the given id, and the value is the point and reason
     * if the user in the review map. Otherwise, return a map with 0 as key, and a list of string with "0" and
     * empty string as a value.
     */
    public Map<Integer, String[]> getReviewById(int userId){
        if (reviewMap.containsKey(userId)){
            return reviewMap.get(userId);
        }else{
            HashMap<Integer, String[]> map1 = new HashMap<>();
            String[] strings = new String[2];
            strings[0] ="0";
            strings[1] = "";
            map1.put(0,strings);
            return map1;
        }
    }

    /** delete a report from the reportMap
     * @param userId1 the id of user who has been reported
     * @param userId2 the id of user who report the other user
     * @return true if the report is removed from the map successful
     */
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean deleteReport(int userId1, int userId2){
        if (reportMap.containsKey(userId1) && reportMap.get(userId1).containsKey(userId2)){
            if (reportMap.get(userId1).size() == 1){
                reportMap.remove(userId1);
            }
            else{
                reportMap.get(userId1).remove(userId2);
            }
            return true;
        }return false;
    }

    /** delete a review from the reviewMap
     * @param userId1 the id of user who has been reviewed
     * @param userId2 the id of user who reviewed the other user
     * @return true if the review is removed from the map successful
     */
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean deleteReview(int userId1, int userId2){
        if (reviewMap.containsKey(userId1) && reviewMap.get(userId1).containsKey(userId2)){
            if (reviewMap.get(userId1).size() == 1){
                reviewMap.remove(userId1);
            }
            else{
                reviewMap.get(userId1).remove(userId2);
            }
            return true;
        }return false;
    }
}

