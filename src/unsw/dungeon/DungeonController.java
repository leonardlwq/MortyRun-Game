package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    StartScreen startscreen;
    Stage stage;

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
    private DungeonControllerLoader dungeonCL;
    private List<ImageView> attacks;
    private Timeline timing;
    private Timeline moveStop;
    private DungeonScreen dungeonScreen;
	private GameOverScreen gameOverScreen;
    private GameWinScreen gameWinScreen;
    @FXML
    private Label treasureDisplay;
    @FXML
    private Label potionDuration;
    @FXML
    private Label enemyDisplay;
    @FXML
    private Label switchDisplay;
    @FXML
    private Label swordDisplay;

    @FXML
    private Label GoalDisplay;

    @FXML 
    private ImageView playerStatus;

    @FXML
    private ImageView keyDisplay;

    @FXML
    private StackPane alignmentAttempt;

    @FXML
    private Text credits;
    //private int pickedUpTreasure;

    @FXML
    private StackPane alignmentAttemptTop;

    @FXML
    private Text GameTitle;

    @FXML
    private Button menuBtn;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities,  DungeonControllerLoader dungeonCL) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.attacks = new ArrayList<ImageView>();
        this.dungeonCL = dungeonCL;
        this.dungeon.set_dc(this);
        this.moveStop = new Timeline();
        this.timing = new Timeline();
        //this.pickedUpTreasure = 0;
        this.treasureDisplay = new Label(" --:--");
        this.potionDuration = new Label(" : --");
        this.GoalDisplay = new Label("Objectives: ");
        this.enemyDisplay = new Label(" --/-- ");
        this.switchDisplay = new Label(" --/-- ");
        //borderpane.setBottom(credits);
        //borderpane.setAlignment(credits, Pos.CENTER);
        

        timing.setCycleCount(Timeline.INDEFINITE);
        KeyFrame autoTick = new KeyFrame(Duration.millis(700), e ->{dungeon.tick();} );
        timing.getKeyFrames().add(autoTick);
        
        KeyFrame restrictMovement = new KeyFrame(Duration.millis(200), e ->{removeAttacks();} );
    	moveStop.getKeyFrames().add(restrictMovement);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/space.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight() - 1; y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }
        keyDisplay.setOpacity(0.3);
        dungeon.displayGoals();
        

        //Node testing = squares.getChildren().get(dungeon.getHeight() *dungeon.getHeight() + inventoryPosition * 2);
        //((Label) testing).setText("YOLO!!");
        //treasureDisplay.setText(" :10");
        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
        for(Entity e : dungeon.getAllEntities())
        {
            if(e instanceof Boulder){
                ((Boulder)e).checkStartLocation(dungeon);
                //System.out.println("TEST");
            }
        }
        dungeon.setPlayerView(false);
        credits.setTextAlignment(TextAlignment.CENTER);
        GameTitle.setTextAlignment(TextAlignment.CENTER);
        menuBtn.setVisible(false);
        menuBtn.setDisable(true);

    }
    @FXML
    void returnToMain(ActionEvent event){
        startscreen.start();
    }

    public void setStartScreen(StartScreen startscreen) {
		this.startscreen = startscreen;
		
	}
    public void updateGoalDisplay(String output){
        GoalDisplay.setText(output);
    }

    public void updateKeyAppearance(boolean status)
    {
        if(status){
            keyDisplay.setOpacity(1.0);
        }
        else{
            keyDisplay.setOpacity(0.3);
        }
    }

    public void updateSwordDurability(int uses){
        if(uses != 0)
        {
            swordDisplay.setText(" Uses Left: " + uses);
        } else{
            swordDisplay.setText(" Uses Left: --");
        }
        
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
    	if (moveStop.getStatus() != Status.RUNNING) {
    		processKey(event);
    		moveStop.play();
    	}
    }

    public void updatePotion(int p){
        potionDuration.setText(" : " + p);
    }

    public void updateTreasure(boolean isDone, int done, int total){
        //pickedUpTreasure++;
        treasureDisplay.setText(" " + done + " / " + total);
    }

    public void updateEnemy(boolean isDone, int done, int total){
        enemyDisplay.setText(" " + done + " / " + total);
    }

    public void updateSwitch(boolean isDone, int done, int total){
        switchDisplay.setText(" " + done + " / " + total);
    }
    

    public void processKey(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            if(!dungeon.getPauseStatus())
                player.moveUp();
            break;
        case DOWN:
            if(dungeon.getPauseStatus())
                player.moveDown();
            break;
        case LEFT:
            if(dungeon.getPauseStatus())
                player.moveLeft();
            break;
        case RIGHT:
            if(dungeon.getPauseStatus())
                player.moveRight();
            break;
        case X:
            if(dungeon.getPauseStatus())
        	    player.useSword();
        	break;
        case D:
            if(dungeon.getPauseStatus())
                player.dropKey();
            break;
        case SPACE:
            dungeon.updatePause();
            if(dungeon.getPauseStatus()){
                GameTitle.setText("GAME PAUSED");
                credits.setText("GAME PAUSED");
                squares.setOpacity(0.3);
                menuBtn.setVisible(true);
                menuBtn.setDisable(false);
                timing.pause();
            }
            else{
                GameTitle.setText("RUN!!");
                credits.setText("TEAM-SG");
                squares.setOpacity(1.0);
                menuBtn.setVisible(false);
                menuBtn.setDisable(true);
                timing.play();
            }

        default:
            break;
        }
    }

    private void removeAttacks() {
		for (ImageView attack: attacks) {
			squares.getChildren().remove(attack);
		}
		
		attacks.removeAll(attacks);
	}

    public void removeEntity(Entity entity) {
		ImageView tmp = dungeonCL.getEntityView(entity);
		squares.getChildren().remove(tmp);
	}

	public void addEntity(Entity entity) {
		ImageView tmp = dungeonCL.getEntityView(entity);
		squares.getChildren().add(tmp);
		
    }

    public void updateDoor(Entity e) {
		ImageView oldDoor = dungeonCL.getEntityView(e);
		squares.getChildren().remove(oldDoor);
		ImageView newDoor = dungeonCL.unlockDoor(e);
		squares.getChildren().add(newDoor);
		
	}
    

    public void addSwordAttack(int i, int j) {
		ImageView attack = dungeonCL.getAttack();
		GridPane.setColumnIndex(attack, i);
        GridPane.setRowIndex(attack, j);
        squares.getChildren().add(attack);
        attacks.add(attack);
    }

    public void setPlayerView(boolean potionState) {
		ImageView oldPlayer = dungeonCL.getEntityView(player);
		squares.getChildren().remove(oldPlayer);
        ImageView newPlayer = dungeonCL.getNewPlayer(player, potionState, player.hasSword());
        playerStatus.setImage(newPlayer.getImage());
		squares.getChildren().add(newPlayer);
		
    }
    

    public void start() {
		this.dungeonScreen.start();
		
	}

	public void setDungeonScreen(DungeonScreen dungeonScreen) {
		this.dungeonScreen = dungeonScreen;
		
	}

	public void startEnemy() {
		timing.play();
	}

	public void winningScreen() {
		timing.stop();
		moveStop.stop();
		gameWinScreen.start();
	}

	public void gameOverScreen() {
		timing.stop();
		moveStop.stop();
		gameOverScreen.start();
	}

	public void setGameOverScreen(GameOverScreen gameOverScreen) {
		this.gameOverScreen = gameOverScreen;
		
	}

	public void setGameWinScreen(GameWinScreen gameWinScreen) {
		this.gameWinScreen = gameWinScreen;
		
	}
    
}
