package bookTradeSystem;

public class SystemMessage {


    public SystemMessage(){

    }


    protected void msgForMeetingDNE(DisplaySystem ds) {
        ds.printOut("This meeting doesn't exist in the system.");
    }


    protected void msgForNothing(DisplaySystem ds){
        ds.printOut("There's nothing here.");
        ds.printOut("\n");
    }


    protected void msgForNothing(String nextPart, DisplaySystem ds){
        ds.printOut("There's nothing " + nextPart + " .");
        ds.printOut("\n");
    }



    protected void lockMessageForThreshold(DisplaySystem ds) {
        ds.printOut("This option is locked");
        ds.printOut("You have reached the" + User.getMaxNumTransactionIncomplete() + "transactions a week limit");
        ds.printOut("\n");
    }

    protected void msgForThresholdValue(int currentVal, DisplaySystem ds)
    {
        ds.printOut("The current threshold value is " + currentVal);
    }

}
