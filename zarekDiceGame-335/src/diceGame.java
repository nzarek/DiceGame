

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class diceGame extends Application {
	
	public static void main(String[] args) {
		
		// Start the JavaFX application by calling launch()
		launch(args);
	}
   
	//override the init method
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage myStage) {
		// give the stage a title
		myStage.setTitle("Dice Game");
		
		//create a root node, in this case a flow layout
		//is used, but several alternatives exist
		GridPane rootNode = new GridPane();
		rootNode.setPadding(new Insets(10, 10, 10, 10));
		
		//set gap between panels in grid
		rootNode.setVgap(10);
		rootNode.setHgap(5);
		
		//create a scene
		Scene myScene = new Scene (rootNode, 600, 250);
		
		//set the stage on the scene
		myStage.setScene(myScene);
		
		//default die setup
		Image intialImage = new Image("images/one.png", 100, 100, true, true);
		
		ImageView imageView = new ImageView(intialImage); 
		ImageView imageView2 = new ImageView(intialImage); 
		
		//hbox for each die
		HBox dieOne = new HBox(10);
		dieOne.getChildren().add(imageView);
		
		HBox dieTwo = new HBox(10);
		dieTwo.getChildren().add(imageView2);
		
		//score arrays to hold scores 
		int[] userScore = {0};
		int[] houseScore = {0};
		
		//label setup 
		Label lblBet = new Label("Your bet: ");
		lblBet.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0), 
				BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		lblBet.setMinWidth(25);
		Label lblResult = new Label("                                                    ");
		lblResult.setBorder(new Border(new BorderStroke(Color.rgb(80, 80, 160, 1.0),
				BorderStrokeStyle.SOLID, new CornerRadii(3.0), BorderStroke.MEDIUM)));
		lblResult.setMinWidth(300);
		Label lblScores = new Label("Player: " + userScore[0] + "\nHouse: " + houseScore[0]);
		lblScores.setMinWidth(75);
		
		
		
		//textfield setup
		TextField tfBet = new TextField();
		tfBet.setMaxWidth(50);
		
		//button setup
		Button btnRoll = new Button("Roll Dice");
		btnRoll.setMinWidth(75);
		Button btnExit = new Button("Exit");
		
		//roll button disabled until valid entry
		btnRoll.setDisable(true);

		
		//keypress filter for validation
		myScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			System.out.println("Pressed: " + event.getCode());

			if ((event.getCode().isDigitKey() || event.getCode().equals(KeyCode.DELETE) || 
					event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.ENTER))) {

				tfBet.setStyle("");
				lblResult.setText("                                                    ");
				btnRoll.setDisable(false);
	
			} else {
				
				event.consume();
				btnRoll.setDisable(true);
				tfBet.setStyle("-fx-control-inner-background: red"); 
				lblResult.setText("Error! Invalid Entry");

			}
		});
		
		//position elements
		rootNode.add(imageView, 5, 0);  
		rootNode.add(imageView2, 7, 0); 
		GridPane.setColumnSpan(imageView, 2);
		GridPane.setColumnSpan(imageView2, 2);
		rootNode.add(lblBet, 0, 1);
		rootNode.add(tfBet, 2, 1);
		rootNode.add(btnRoll, 5, 1);
		rootNode.add(btnExit, 6, 1);
		rootNode.add(lblResult,7, 1);
		rootNode.add(lblScores, 0, 0);
		
		
		
		btnRoll.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle (ActionEvent ae) {
				
				//random numbers for die
				int dieValue1 = ThreadLocalRandom.current().nextInt(0, 5 + 1);
				int dieValue2 = ThreadLocalRandom.current().nextInt(0, 5 + 1);
				
				
				//validate for character input following integer
				if (tfBet.getText().matches("\\d+") == false) {
					btnRoll.setDisable(true);
					tfBet.setText("0");
				} 
					
				int userEntry = Integer.parseInt(tfBet.getText());
				
				
				int rollTotal = dieValue1 + dieValue2 + 2;
				
				//declare images
				Image d1 = new Image("images/one.png", 100, 100, true, true);
				Image d2 = new Image("images/two.png", 100, 100, true, true);
				Image d3 = new Image("images/three.png", 100, 100, true, true);
				Image d4 = new Image("images/four.png", 100, 100, true, true);
				Image d5 = new Image("images/five.png", 100, 100, true, true);
				Image d6 = new Image("images/six.png", 100, 100, true, true); 
				
				//image array
				Image dieArray[]=new Image[]{d1, d2, d3, d4, d5, d6};
				
				//validate to determine within range
				if (userEntry < 2 || userEntry > 12) {
					tfBet.setStyle("-fx-control-inner-background: red");//background color change if not within range
					lblResult.setText("Error! Invalid Entry");
			     } else {
			    	 tfBet.setStyle(""); 
			    	 imageView.setImage(dieArray[dieValue1]);//change die 
					 imageView2.setImage(dieArray[dieValue2]);
					 
					 //validate user win/loss and print to label 
					 if (userEntry == rollTotal) {
							
						    userScore[0] = userScore[0] + 1;
						 	lblResult.setText("You win. You bet on " + Integer.toString(userEntry) + ", and the dice rolled " +Integer.toString(rollTotal) + ".");
							lblScores.setText("Player: " + userScore[0] + "\nHouse: " + houseScore[0]);
							
						}
						else { 
							houseScore[0] = houseScore[0] + 1;
							lblResult.setText("The house wins. You bet on " + Integer.toString(userEntry) + ", but the dice rolled " +Integer.toString(rollTotal) + ".");
							lblScores.setText("Player: " + userScore[0] + "\nHouse: " + houseScore[0]);
						}
			     }
				
				} 
		});
	
		//exit application button
		btnExit.setOnAction(new EventHandler<ActionEvent>() { 
			public void handle (ActionEvent ae) {
				Platform.exit();
			} 
		});
		
		//show the stage and its scene
		myStage.show(); 
	}
	
	
	@Override
	public void stop() {
	}

}