package Managers.FeedbackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewReportManager {
    // the key - id of user who has been reported, the value is the list of users who report the user.
    private Map<Integer, ArrayList<Integer>> reportMap;
    // the key is the id of the user who is reviewed, the value is the list of point the user get.
    private Map<Integer, ArrayList<Integer>> reviewMap;

    public ReviewReportManager(){
        reportMap = new HashMap<>();
        reviewMap = new HashMap<>();
    }
    public Map<Integer, ArrayList<Integer>> getReportMap(){
        return reportMap;
    }
    public Map<Integer, ArrayList<Integer>> getReviewMap(){
        return reviewMap;
    }
    // the userId1 is the id of the user who is reviewed
    public void updateReview(Integer userId1, Integer point ){
        if(reviewMap.containsKey(userId1)){
          reviewMap.get(userId1).add(point);
        }else{
            ArrayList<Integer> listPoint = new ArrayList<>();
            listPoint.add(point);
            reviewMap.put(userId1,listPoint);
        }
    }
    public double calculateRate(int userId){
        if (!reviewMap.containsKey(userId)){
            return -1.0;
        }else{
            ArrayList<Integer> listPoint = reviewMap.get(userId);
            Integer sum = 0;
            for(Integer point: listPoint){
                sum += point;
            }
            return sum.doubleValue() / listPoint.size(); }
    }
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
    public boolean updateReport(int userId1, int userId2){
        if (reportMap.containsKey(userId1) && reportMap.get(userId1).contains(userId2)){
            return false;
        }else if(reportMap.containsKey(userId1) && !reportMap.get(userId1).contains(userId2)){
            reportMap.get(userId1).add(userId2);
            return true;
        }else {
            ArrayList<Integer> listReport = new ArrayList<>();
            listReport.add(userId2);
            reportMap.put(userId1, listReport);
            return true;
        }
        }
    // userId1 is the id of the user who has been reported, the userId2 is the id of user who report the other user
     public boolean deleteReport(int userId1, int userId2){
         if (reportMap.containsKey(userId1) && reportMap.get(userId1).contains(userId2)){
             if (reportMap.get(userId1).size() == 1){
                 reportMap.remove(userId1);
             }
             else{
                 reportMap.get(userId1).remove(userId2);
             }
             return true;
         }return false;
     }
    }


