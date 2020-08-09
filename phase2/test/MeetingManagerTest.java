import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import static org.junit.Assert.*;
import managers.trademanager.TradeManager;
import org.junit.Test;


public class MeetingManagerTest {

    @Test
    public void testGetMeetingsById(){
        TradeManager tradeManager = new TradeManager();
        tradeManager.addTrade(tradeManager.createTrade(1,2, 1,"Temporary",true, 1));
        MeetingManager meetingManager = new MeetingManager();
        meetingManager.addMeeting(1,1,2,1,tradeManager);
        assertEquals(1,meetingManager.getMeetingsByUserId(1).size());
        assertEquals(0,meetingManager.getMeetingsByUserId(3).size());
        assertEquals(1,meetingManager.getUnCompleteMeeting(1,tradeManager).size());
        assertEquals(0,meetingManager.getUnCompleteMeeting(3,tradeManager).size());
        assertEquals(1,meetingManager.getUnConfirmTimePlace(1,tradeManager).size());
        assertEquals(0,meetingManager.getUnConfirmTimePlace(3,tradeManager).size());
        assertEquals(0,meetingManager.getUnConfirmMeeting(1).size());
    }
    @Test
    public void testEditTime(){
        TradeManager tradeManager1 = new TradeManager();
        tradeManager1.addTrade(tradeManager1.createTrade(1,2, 1,"Temporary",true, 1));
        MeetingManager meetingManager1 = new MeetingManager();
        meetingManager1.addMeeting(1,1,2,1,tradeManager1);
        Meeting meeting = meetingManager1.getMeetingByIdNum(1,1);
        boolean check1 = meetingManager1.confirmTimePlace(meeting, 1,3);
        assertFalse(check1);
        boolean check2 = meetingManager1.editTimePlace(meeting,1,2020,4,4,4,4,4,"toronto", 3);
        assertTrue(check2);
        boolean check3 = meetingManager1.confirmTimePlace(meeting, 1,3);
        assertFalse(check3);
        boolean check4 =meetingManager1.confirmTimePlace(meeting, 2,3);
        assertTrue(check4);
        assertEquals(1,meetingManager1.getUnConfirmMeeting(1).size());
        tradeManager1.addTrade(tradeManager1.createTrade(1,2, 2,"Temporary",true, 2));
        meetingManager1.addMeeting(2,1,2,1,tradeManager1);
        Meeting meeting1 = meetingManager1.getMeetingByIdNum(2,1);
        meetingManager1.editTimePlace(meeting1,1,2020,4,4,4,4,4,"toronto", 3);
        meetingManager1.editTimePlace(meeting1,2,2020,4,4,4,4,4,"toronto", 3);
        meetingManager1.editTimePlace(meeting1,1,2020,4,4,4,4,4,"toronto", 3);
        meetingManager1.editTimePlace(meeting1,2,2020,4,4,4,4,4,"toronto", 3);
        meetingManager1.editTimePlace(meeting1,1,2020,4,4,4,4,4,"toronto", 3);
        meetingManager1.editTimePlace(meeting1,2,2020,4,4,4,4,4,"toronto", 3);
        boolean check5 = meetingManager1.editTimePlace(meeting1,1,2020,4,4,4,4,4,"toronto", 3);
        assertFalse(check5);
        boolean check6 =meetingManager1.confirmTimePlace(meeting, 2,3);
        assertFalse(check6);
        String check7 = meetingManager1.getEditOverThreshold(tradeManager1,meeting1,3);
        assertEquals("Your transaction with id 2 has been cancelled.", check7);
    }
    @Test
    public void testCompleteMeeting(){
        TradeManager tradeManager2 = new TradeManager();
        tradeManager2.addTrade( tradeManager2.createTrade(1,2, 1,"Temporary",true, 1));
        MeetingManager meetingManager2 = new MeetingManager();
        meetingManager2.addMeeting(1,1,2,1,tradeManager2);
        Meeting meeting = meetingManager2.getMeetingByIdNum(1,1);
        meetingManager2.editTimePlace(meeting,1,2020,4,5,5,5,5,"t",3);
        meetingManager2.confirmTimePlace(meeting,2,3);
        boolean check = meetingManager2.setMeetingConfirm(tradeManager2,meeting,1,3);
        assertTrue(check);
        boolean check1 = meetingManager2.setMeetingConfirm(tradeManager2,meeting,2,3);
        assertTrue(check1);
        boolean check2 = meetingManager2.setMeetingConfirm(tradeManager2,meeting,3,3);
        assertFalse(check2);
        boolean check3 = meetingManager2.setMeetingConfirm(tradeManager2,meeting,2,3);
        assertFalse(check3);
        assertEquals(1,meetingManager2.getCompleteMeeting(1).size());
        Meeting meeting2 = meetingManager2.getMeetingByIdNum(1,2);
        assertEquals("t",meeting2.getPlace());
    }
    @Test
    public void testUndo(){TradeManager tradeManager2 = new TradeManager();
        tradeManager2.addTrade( tradeManager2.createTrade(1,2, 1,"Temporary",true, 1));
        MeetingManager meetingManager2 = new MeetingManager();
        meetingManager2.addMeeting(1,1,2,1,tradeManager2);
        Meeting meeting = meetingManager2.getMeetingByIdNum(1,1);
        meetingManager2.editTimePlace(meeting,1,2020,4,5,5,5,5,"t",3);
        meetingManager2.confirmTimePlace(meeting,2,3);
        meetingManager2.setMeetingConfirm(tradeManager2,meeting,1,3);
        meetingManager2.setMeetingConfirm(tradeManager2,meeting,2,3);
        meetingManager2.undoConfirmTookPlace(1,1,2);
        assertFalse(meetingManager2.getMeetingByIdNum(1,2).getTradeId()!=0);
        assertFalse(meeting.getMeetingConfirm().get(2));
        assertEquals(0,meetingManager2.getMeetingByIdNum(1,2).getTradeId());


    }
}


