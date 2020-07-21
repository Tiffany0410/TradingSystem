package Managers.FeedbackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewReportManager {
    // the key - id of user report others, the value is the list of users who is reported by the user.
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
}
