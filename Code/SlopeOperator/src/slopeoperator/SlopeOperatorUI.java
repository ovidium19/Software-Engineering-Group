//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : SlopeOperator
//  @ File Name : SlopeOperatorUI.java
//  @ Date : 15/02/2017
//  @ Author : Genaro Bedenko
//
//

package slopeoperator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.*;

/*
 *Class that sets out the user interface
 *
 * @author Genaro Bedenko
 */
public class SlopeOperatorUI {
        
    private JFrame mainFrame = new JFrame("Sphere Booking & Checking In System - Provided by InvoTech");
    private JFrame bookingFrame = new JFrame("Booking Session");
    private JLabel headerLabel;
    private JLabel mainStatusLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
       
    BookingController bookingControlerConnection = new BookingController(); 
    ArrayList bookingList = new ArrayList();
        
    public SlopeOperatorUI() {
    }

    
   public void setup(){
      mainFrame = new JFrame("Sphere Booking & Checking In System - Provided by InvoTech");
      mainFrame.setSize(500,500);
      mainFrame.setLayout(new GridLayout(4, 1));

      headerLabel = new JLabel("",JLabel.CENTER );
      mainStatusLabel = new JLabel("",JLabel.CENTER);        

      mainStatusLabel.setSize(400,400);
      
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(5, 1));

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(mainStatusLabel);      
      mainFrame.setVisible(true);  
      
      mainFrame.setVisible(true);
   }
    
   public void setup(JFrame frame){
      frame.setSize(500,500);
      frame.setLayout(new GridLayout(4, 1));

      headerLabel = new JLabel("",JLabel.CENTER);
      statusLabel = new JLabel("",JLabel.CENTER);        

      statusLabel.setSize(350,100);
      
      
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(4, 1));

      frame.add(headerLabel);
      frame.add(controlPanel);
      frame.add(statusLabel);      
      frame.setVisible(true);  
      
      frame.setVisible(true);
   }

   private void setVisibility(JFrame frame, boolean bool){
      frame.setVisible(bool); 
   }
   
   public void showEventDemo(){
      setup();
      
      JLabel userText = new JLabel("SLOPE OPERATOR WELCOME SCREEN", SwingConstants.CENTER);

      JButton registerButton = new JButton("Register a customer");
      JButton bookButton = new JButton("Book a session");
      JButton checkInButton = new JButton("Check in customer");
      JButton viewScheduleButton = new JButton("View Schedule");
      JButton addSessionButton = new JButton("Add a Session");
      
      registerButton.setActionCommand("Register a customer");
      bookButton.setActionCommand("Book a session");
      checkInButton.setActionCommand("Check in customer");
      viewScheduleButton.setActionCommand("View Schedule");
      
      registerButton.addActionListener(new ButtonClickListener()); 
      bookButton.addActionListener(new ButtonClickListener());
      checkInButton.addActionListener(new ButtonClickListener()); 
      viewScheduleButton.addActionListener(new ButtonClickListener()); 
      
      controlPanel.add(userText);
      controlPanel.add(registerButton);
      controlPanel.add(bookButton);
      controlPanel.add(checkInButton);
      controlPanel.add(viewScheduleButton);

      mainFrame.setVisible(true);
      bookingFrame.setVisible(false);
      
   }
   

    private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if( command.equals("Register a customer"))  {
            registerCustomer(); 
         }
         else if( command.equals("Book a session") )  {
            bookSession();           
         }
         else if( command.equals("Check in customer") )  {
            checkInCustomer(); 
         }
         else if(command.equals("View Schedule")) {
            viewSchedule();
         }
      }		
   }
    
    public void registerCustomer() {
        
        mainStatusLabel.setText("Register a customer clicked.");
    }
    public void bookSession() {

      mainFrame.setVisible(true);
      
      setup(bookingFrame);
      
      JLabel  customerIDlabel= new JLabel("Enter the Customer's ID: ", JLabel.CENTER);
      JLabel  sessionTypelabel= new JLabel("Choose Instructor (if required): ", JLabel.CENTER);
      
      String[] instructors = { "No Instructor", "A. Adams","B. Barry", "C. Charlie","D. Daniels"};
      
      JComboBox<String> instructorsDropDown = new JComboBox<String>(instructors);

      instructorsDropDown.setVisible(true);
    
      
      JLabel  dateLabel = new JLabel("Choose Date: ", JLabel.CENTER);
      JLabel  timeSlotLabel = new JLabel("Choose Time Slot: ", JLabel.CENTER);
      final JTextField customerID = new JTextField(6);
      final JTextField sessionType = new JTextField(10);
      final JTextField date = new JTextField(20);      
      final JTextField timeSlot = new JTextField(20);     

      JButton addButton = new JButton("Book Session");
      addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String theCustomerID = customerID.getText();
            String theSessionType = sessionType.getText();
            String theDate = date.getText();
            String theTimeSlot = timeSlot.getText();

            int num = Integer.parseInt(theCustomerID);
            int num2 = 123123;
            bookingControlerConnection.book(num, num2);
            
            String data = "A session has been booked for customer: " + customerID.getText();
            data += ", name: " + sessionType.getText();
            data += ", speciality: "+ date.getText(); 
            
            statusLabel.setText(data);        
         }        
      }); 

      JButton backButton = new JButton("Back");
      backButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
             setVisibility(mainFrame, true);
             setVisibility(bookingFrame, false);
         }        
      });       
      controlPanel.add(customerIDlabel);
      controlPanel.add(customerID);
      controlPanel.add(sessionTypelabel);
      controlPanel.add(instructorsDropDown);
      controlPanel.add(dateLabel);       
      controlPanel.add(date);
      controlPanel.add(addButton);
      controlPanel.add(backButton); 
    
      bookingFrame.setVisible(true);

    }  
    public void checkInCustomer() {
        
        mainStatusLabel.setText("Check in customer clicked.");
    }
    public void viewSchedule() {
        
        mainStatusLabel.setText("View Schedule clicked.");
    }
}