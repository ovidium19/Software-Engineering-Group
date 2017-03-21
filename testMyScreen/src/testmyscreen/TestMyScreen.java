/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testmyscreen;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author sofronim
 */
public class TestMyScreen extends Application {
    private Stage theStage;
    
    private Scene makeDetailsScene(){
        Label title = new Label("Customers Profile");
        BorderPane root= new BorderPane();
        root.setTop(title);
        
        GridPane centerRoot=new GridPane();
        centerRoot.setHgap(10);
        centerRoot.setVgap(10);
        Label fname = new Label("First Name:");
        Label lname = new Label("Last Name:");
        Label email=new Label("Email:");
        Label tphone = new Label("Telephone:");
        Label membership = new Label("member:");
        Label paid = new Label("Paid: ");
        Label fnameFromDb=new Label("Michael");
        Label lnameFromDb=new Label("Mike");
        Label emailFromDb=new Label("aaa@aaa.com");
        Label membFromDb=new Label("free");
        Label tphoneFromDb=new Label("074423232131");
        Label paidFromDb=new Label("no");
        
        //centerRoot.setPadding(new Insets(20,0,0,0));
        centerRoot.add(fname, 0, 0);
        centerRoot.add(fnameFromDb,1,0);
        centerRoot.add(lname,0,1);
        centerRoot.add(lnameFromDb,1,1);
        centerRoot.add(email, 0,2);
        centerRoot.add(emailFromDb,1,2);
        centerRoot.add(tphone,0,3);
        centerRoot.add(tphoneFromDb,1,3);
        centerRoot.add(membership,0,4);
        centerRoot.add(membFromDb,1,4);
        centerRoot.add(paid,0,5);
        centerRoot.add(paidFromDb,1,5);
        //centerRoot.setAlignment(Pos.TOP_CENTER);
        //BorderPane.setAlignment(centerRoot, Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setPadding(new Insets(20,20,20,20));
        root.setCenter(centerRoot);
        Scene scene=new Scene(root,450,450);
        return scene;
    }
    
    @Override
    public void start(Stage primaryStage) {
        theStage=primaryStage;
        Label welcomeText = new Label();
        welcomeText.setText("Welcome to Sphere Booking & Checking In System");
        welcomeText.setAlignment(Pos.TOP_CENTER);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        welcomeText.setPadding(new Insets(12,5,20,5));
        
        
        
        Label TitleText = new Label();
        TitleText.setText("Check In Customer");
        TitleText.setAlignment(Pos.TOP_CENTER);
        TitleText.setTextAlignment(TextAlignment.CENTER);
        TitleText.setPadding(new Insets(12,5,20,5));
        
        Label EnterLabel = new Label();
        EnterLabel.setText("Enter Customers ID:  ");
        EnterLabel.setAlignment(Pos.TOP_CENTER);
        EnterLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField EnterText = new TextField();
        EnterText.setAlignment(Pos.TOP_CENTER);
        
        HBox EnterHBox = new HBox();
        EnterHBox.getChildren().addAll(EnterLabel, EnterText);
        EnterHBox.setAlignment(Pos.TOP_CENTER);
       
        

        Button SearchButton = new Button();
        SearchButton.setText("Search");
        SearchButton.setAlignment(Pos.TOP_CENTER);
        SearchButton.setTextAlignment(TextAlignment.CENTER);
        
        
        

        
        
        /*
           SearchButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String theCustomerID = EnterText.getText();
                
               /boolean isACustomer = customerControllerConnection.checkCustomerID(conn, theCustomerID);
            
                if(isACustomer==false) {
                    
                    customerStatusLabel.setText("The customer with that ID does not exist");
                }
            }
        });*/
        SearchButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                theStage.setScene(makeDetailsScene());
            }
        });
		
		
		Button BackButton= new Button () ;
		BackButton.setText("Back") ;
		BackButton.setAlignment(Pos.TOP_CENTER);
		BackButton.setTextAlignment(TextAlignment.CENTER);
		
	
        HBox buttons = new HBox();
        buttons.getChildren().addAll(SearchButton,BackButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        
        VBox root = new VBox();
        root.getChildren().addAll(TitleText, EnterHBox, buttons);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        
        Scene scene = new Scene(root, 500, 450);
        theStage.setScene(scene);
        theStage.show();
        //return(scene); 
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void ComboBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}   

