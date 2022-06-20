public class SingleGameController {

    private SingleGamePanel gamePanel;

    public SingleGamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(SingleGamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void ClickAllGrid(){
        for (int i = 0; i <gamePanel.getChessboard().length; i++) {
            for (int j = 0; j <gamePanel.getChessboard()[0].length ; j++) {
                gamePanel.getMineField()[i][j].setStatus(GridStatus.Clicked);
            }
        }
        gamePanel.repaint();
    }



    public void readFileData(String fileName) {
        //todo: read date from file

    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file
    }
}
