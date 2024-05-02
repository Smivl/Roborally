package controller.player;


import com.google.gson.Gson;
import model.board.robot.Robot;
import model.player.Player;
import model.register.cards.Card;
import view.player.PlayerView;

public class PlayerController {

    public final boolean isComputer;

    private Player player;
    private PlayerView playerView;

    public PlayerController(Player model, PlayerView view, boolean isComputer)
    {
        player = model;
        playerView = view;
        this.isComputer = isComputer;
    }

    public void onUpdate()
    {
        player.onUpdate();
    }
    public void onEndOfTurn()
    {

        if(player.getQueueSize() == 0){
            player.getHandCards().setCards(playerView.getHandCardsView().getCards());
            for (Card card : playerView.getCardQueueView().getCards()) player.queueCard(card);
        }
    }

    public void setPlayerRobot(Robot robot) {
        player.setRobot(robot);
    }
    public String getPlayerName()
    {
        return player.player_name;
    }
    public PlayerView getView() {
        return playerView;
    }
    public String getRobotName() {
        if(player.getRobot() != null)
        {
            return player.getRobot().getName();
        }else{
            return "NO ROBOT";
        }
    }
    @Override
    public String toString(){
        return player.toString();
    }

    public void loadCards(String[] handCards, String[] cardQueue) {
        player.loadCardData(handCards, cardQueue);
        playerView.getHandCardsView().setCards(player.getHandCards().getCards());
    }
}
