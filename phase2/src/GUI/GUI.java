package GUI;

import controllers.maincontrollers.TradingSystem;

public class GUI {
    private TradingSystem tradingSystem;

    public void GUI(TradingSystem tradingSystem){
        this.tradingSystem = tradingSystem;
    }

    public void tradingSystemInitMenu(){
        tradingSystemInitMenuGUI tradingSystemInitMenuGUI = new tradingSystemInitMenuGUI(this.tradingSystem);
        tradingSystemInitMenuGUI.run(this.tradingSystem);
    }
}
