package gui;

public class GUIUserInputInfo {
    private String tempUserInput;

    public GUIUserInputInfo() {
        this.tempUserInput = "";
    }

    public void tempSaveUserInput(String text) {
        this.tempUserInput = text;
    }

    public String getTempUserInput(){
        return this.tempUserInput;
    }
}
