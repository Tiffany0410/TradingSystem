import managers.feedbackmanager.FeedbackManager;
import org.junit.Test;
import static org.junit.Assert.*;

public class FeedbackManagerTest  {
    @Test
    public void testReview(){
        FeedbackManager feedbackManager = new FeedbackManager();
        feedbackManager.setReview(1,2,5,"bad");
        feedbackManager.setReview(1,3,6,"fine");
        assertFalse(feedbackManager.setReview(1,3,6,"fine"));
        assertEquals(5.5,feedbackManager.calculateRate(1),0.1);
        assertEquals(-1.0, feedbackManager.calculateRate(2),0.1);
        assertTrue(feedbackManager.haveReview(1));
        assertFalse(feedbackManager.haveReview(2));
        assertTrue(feedbackManager.haveReview(1,2));
        assertFalse(feedbackManager.haveReview(2,1));
        assertTrue(feedbackManager.deleteReview(1,3));
        assertEquals(1,feedbackManager.getReviewById(1).size());
        assertEquals(0,feedbackManager.getReviewById(3).size());
    }
    @Test
    public void testReport(){
        FeedbackManager feedbackManager = new FeedbackManager();
        feedbackManager.updateReport(1,2,"a");
        feedbackManager.updateReport(1,3,"b");
        assertTrue(feedbackManager.haveReport(1,3));
        assertTrue(feedbackManager.updateReport(1,4,"hh"));
        assertFalse(feedbackManager.updateReport(1,2,"ss"));
        //assertEquals(3,feedbackManager.getReportById(1).size());
        assertTrue(feedbackManager.deleteReport(1,2));
        assertFalse(feedbackManager.deleteReport(1,2));
        assertFalse(feedbackManager.deleteReport(2,1));
        //assertEquals(2,feedbackManager.getReportById(1).size());

    }}