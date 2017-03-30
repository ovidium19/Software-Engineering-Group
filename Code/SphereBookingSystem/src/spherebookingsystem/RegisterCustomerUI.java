/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spherebookingsystem;

import java.sql.Connection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Genaro
 */
public class RegisterCustomerUI {
    
    private static Connection conn;
    private static Scene mainScene;    
    private static Stage theStage;
    private Session tempSession;
    
    private RadioButton chk = new RadioButton();
    private CustomerController customerControllerConnection = new CustomerController();
    
    private static RegisterCustomerUI instance = null;
    private RegisterCustomerUI(Stage primaryStage, Connection con){
        
        theStage=primaryStage;
        conn=con;
        tempSession=new Session();
    }
    
    public static RegisterCustomerUI getInstance(Stage stage,Connection con){
        
        if (instance==null){
            instance=new RegisterCustomerUI(stage,con);
        }
        return instance;
    }
    
    // Creates the user interface for registering a customer
    public Scene makeRegisterScreen() {
        
        Label TitleText = new Label();
        TitleText.setText("Customer Registration");
        TitleText.setAlignment(Pos.TOP_CENTER);
        TitleText.setTextAlignment(TextAlignment.CENTER);
        TitleText.setPadding(new Insets(12,5,20,5));
        
        Label FirstNameLabel = new Label();
        FirstNameLabel.setText("First Name:  ");
        FirstNameLabel.setAlignment(Pos.TOP_CENTER);
        FirstNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField FirstNameText = new TextField();
        FirstNameText.setAlignment(Pos.TOP_CENTER);
        
        /*FirstNameText.textProperty().addListener((obs,o,n)->{
            try{
                
                if(n[0] >= 'a')
                //String.parseString(n);
                //phoneNoText.setText("");
                
            }
            catch (NumberFormatException ex){
                FirstNameText.clear();
            }
        });*/
        
        HBox FirstNameHBox = new HBox();
        FirstNameHBox.getChildren().addAll(FirstNameLabel, FirstNameText);
        FirstNameHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label LastNameLabel = new Label();
        LastNameLabel.setText("Last Name:  ");
        LastNameLabel.setAlignment(Pos.TOP_CENTER);
        LastNameLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField LastNameText = new TextField();
        LastNameText.setAlignment(Pos.TOP_CENTER);
        
        HBox LastNameHBox = new HBox();
        LastNameHBox.getChildren().addAll(LastNameLabel, LastNameText);
        LastNameHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label EmailLabel = new Label();
        EmailLabel.setText("Email:           ");
        EmailLabel.setAlignment(Pos.TOP_CENTER);
        EmailLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField EmailText = new TextField();
        EmailText.setAlignment(Pos.TOP_CENTER);
        
        HBox EmailHBox = new HBox();
        EmailHBox.getChildren().addAll(EmailLabel, EmailText);
        EmailHBox.setAlignment(Pos.TOP_CENTER);
        
        
        
        Label phoneNoLabel = new Label();
        phoneNoLabel.setText("Phone Number:   ");
        phoneNoLabel.setAlignment(Pos.TOP_CENTER);
        phoneNoLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField phoneNoText = new TextField();
        phoneNoText.setAlignment(Pos.TOP_CENTER);
        
        phoneNoText.textProperty().addListener((obs,o,n)->{
            try{
                Long.parseLong(n);
                //phoneNoText.setText("");
                
            }
            catch (NumberFormatException ex){
                phoneNoText.clear();
            }
        });
        
        HBox phoneNoHBox = new HBox();
        phoneNoHBox.getChildren().addAll(phoneNoLabel, phoneNoText);
        phoneNoHBox.setAlignment(Pos.TOP_CENTER);
        
        Label MemberLabel = new Label();
        MemberLabel.setText("Membership Type:   ");
        MemberLabel.setAlignment(Pos.TOP_CENTER);
        MemberLabel.setTextAlignment(TextAlignment.CENTER);
        
        ToggleGroup MemToggle = new ToggleGroup();
        
        RadioButton MemberButton = new RadioButton();
        MemberButton.setText("Free Membership");
        MemberButton.setAlignment(Pos.TOP_CENTER);
        MemberButton.setTextAlignment(TextAlignment.CENTER);
        MemberButton.setSelected(true);
        
        MemberButton.setToggleGroup(MemToggle);
        chk = (RadioButton)MemberButton.getToggleGroup().getSelectedToggle();
        
        RadioButton MemberButton2 = new RadioButton();
        MemberButton2.setText("Paid Membership");
        MemberButton2.setAlignment(Pos.TOP_CENTER);
        MemberButton2.setTextAlignment(TextAlignment.CENTER);
        
        MemberButton2.setToggleGroup(MemToggle);
        
         MemToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                

            }
        });
        
        HBox MemberHBox = new HBox();
        MemberHBox.getChildren().addAll(MemberLabel, MemberButton, MemberButton2);
        MemberHBox.setAlignment(Pos.TOP_CENTER);
        
        Button SubmitButton = new Button();
        SubmitButton.setText("Submit");
        SubmitButton.setAlignment(Pos.TOP_CENTER);
        SubmitButton.setTextAlignment(TextAlignment.CENTER);
        
        
        Button ConfirmButton = new Button();
        ConfirmButton.setText("Confirm");
        ConfirmButton.setAlignment(Pos.TOP_CENTER);
        ConfirmButton.setTextAlignment(TextAlignment.CENTER);
        ConfirmButton.setVisible(false);
        
        SubmitButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
       
                if (FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() || EmailText.getText().isEmpty() || phoneNoText.getText().isEmpty()){
                    
                    ConfirmButton.setVisible(false);
                }    
                
                else {
                    
                    ConfirmButton.setVisible(true);
                }
            }
        });
        
        ConfirmButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String theFirstName = FirstNameText.getText();
                String theLastName = LastNameText.getText();
                String theEmail = EmailText.getText();
                String thePhoneNo = phoneNoText.getText();
                String theMembership = chk.getText();
                        
                        
                customerControllerConnection.register(conn, theFirstName, theLastName, theEmail, thePhoneNo, theMembership);
            
            }
        });
        
        
        VBox root = new VBox();
        root.getChildren().addAll(TitleText, FirstNameHBox, LastNameHBox, EmailHBox, phoneNoHBox, MemberHBox, SubmitButton, ConfirmButton);
        root.setPadding(new Insets(50,50,50,50));
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(25);
        
        Scene scene = new Scene(root, 500, 450);
        
        return(scene); 
    }
    
}
                                