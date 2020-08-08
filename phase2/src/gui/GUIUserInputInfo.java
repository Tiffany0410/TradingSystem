package gui;

public class GUIUserInputInfo {
    private String tempUserInput;

    public GUIUserInputInfo() {
        this.tempUserInput = "User inputs nothing";
    }

    public void tempSaveUserInput(String text) {
        this.tempUserInput = text;
    }

    public String getTempUserInput(){
        String result = "" + this.tempUserInput;
        this.tempUserInput = "User inputs nothing";
        return result;
    }
}
