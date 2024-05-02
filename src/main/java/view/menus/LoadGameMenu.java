package view.menus;

import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.elements.CustomButton;

import java.io.File;

import static java.lang.Integer.parseInt;

public class LoadGameMenu extends StandardMenu{
    public LoadGameMenu(Stage stage, StandardMenu parent){
        super(stage, parent);

        titleLabel.setText("load game");
        titleLabel.getStyleClass().add("subtitle-label");


        Label label = new Label("Load Last Game State");
        label.getStyleClass().add("inner-label");
        CustomButton singlePlayerButton = new CustomButton("load", this);
        CustomButton backButton = new CustomButton("Back", this);

        buttonBox.getChildren().addAll(label,singlePlayerButton, backButton);

        singlePlayerButton.setOnAction(event -> {
            menuContainer.getGameController().loadGame();
        });

        backButton.setOnAction(event -> {
            goToMenu(parent);
        });
    }

    /*
    Gson gson = new Gson();
    File_operations Fo = new File_operations();
        Fo.Str_to_file("save_game.txt", "", false);
        for(int i = 0; i < players.size(); i ++){
        if(!players.get(i).isComputer){
            //constructors for formatting
            //lam is the lamination between each new piece of data
            /*
            String lam = ";";
            String hand_card_string = "";
            String deck_cards_string = "";
            */

            //fetching a lot of the data that needs to be saved
            /*
            String player_name = players.get(i).getPlayerName();
            String robot_name = players.get(i).getRobotName();
            String robot_pos = players.get(i).get_robot().getPosition().toString();
            String robot_orientation = String.valueOf(players.get(i).get_robot().getOrientation());
            String robot_health = String.valueOf((players.get(i).get_robot().getHealth()));
            String robot_checkpoint_pos = players.get(i).get_robot().getCheckpoint().getPosition().toString();
            */

            //composing a string with all the relevant data for the robot
            //String robot_data = robot_name + lam + robot_pos + lam + robot_orientation + lam + robot_health + lam + robot_checkpoint_pos;

            //making a string off all the cards in the players hand. these valus are seperated by ,
            /*
            for(int j = 0; j < players.get(currentPlayerIndex).Get_player().getHandCards().getCards().size(); j ++){
                String hand_cards_class;
                hand_cards_class = String.valueOf((players.get(currentPlayerIndex).Get_player().getHandCards().getCards().get(j).getClass()));
                String[] cards = hand_cards_class.split("\\.");
                if(cards.length > 0){
                    hand_card_string = hand_card_string + cards[3] + ",";
                }
            }
            */
            //making a string off all the cards in the deck. these values are seperated by ,
            /*
            for(int j = 0; j < players.get(currentPlayerIndex).Get_player().getHandCards().getDeck().getcards().size(); j ++ ){
                String deck_cards_class;
                deck_cards_class = String.valueOf(players.get(currentPlayerIndex).Get_player().getHandCards().getDeck().getcards().get(j).getClass());
                String[] deck_cards = deck_cards_class.split("\\.");
                if(deck_cards.length > 0){
                    //System.out.println(deck_cards);
                    deck_cards_string = deck_cards_string + deck_cards[3] + ",";
                }
            }
            */
            //composes the player string that is saved as a text file
            /*
            String player_str = i + lam +  player_name + lam + hand_card_string + lam + deck_cards_string + lam + robot_data + ";";
            Fo.Str_to_file("save_game.txt", player_str, true);
            */
        //}
    //}

    //uses Gson to output all the bombs on the map to a json file
    /*
    String bombs = gson.toJson(GameBoard.getInstance().getBombTiles());
    Fo.Str_to_file("save_game.txt", bombs, true);
    */
}