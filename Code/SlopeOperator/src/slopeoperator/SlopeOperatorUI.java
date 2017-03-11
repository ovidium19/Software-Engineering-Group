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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *Class that sets out the user interface
 *
 * @author Genaro Bedenko
 */
public class SlopeOperatorUI {
    
    Connection connection;
    ArrayList list = new ArrayList();
    
    
    BookingController bookingControlerConnection = new BookingController();
    CustomerController customerControllerConnection = new CustomerController();
    
    public SlopeOperatorUI(Connection connectionInput) {
        
        connection = connectionInput;
    }
        
    private JFrame mainFrame = new JFrame("Sphere Booking & Checking In System - Provided by InvoTech");
    private final JFrame bookingFrame = new JFrame("Booking Session");
    private final JFrame registerFrame = new JFrame("Register a Customer");
    private final JFrame checkInFrame = new JFrame("Check in Customer");
    private final JFrame viewScheduleFrame = new JFrame("View Schedule");
    
    private JLabel headerLabel;
    private JLabel mainStatusLabel;
    
    private JPanel controlPanel;
   
   // Sets up home window user interface
   public void homeWindowSetup(){
      mainFrame = new JFrame("Sphere Booking & Checking In System - Provided by InvoTech");
      mainFrame.setSize(500,500);
      mainFrame.setLayout(new GridLayout(4, 1));

      headerLabel = new JLabel("SLOPE OPERATOR WELCOME SCREEN",JLabel.CENTER );
      mainStatusLabel = new JLabel("Choose a function",JLabel.CENTER);             
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(5, 1));

      JButton registerButton = new JButton("Register a customer");
      JButton bookButton = new JButton("Book a session");
      JButton checkInButton = new JButton("Check in customer");
      JButton viewScheduleButton = new JButton("View Schedule");
      
      registerButton.setActionCommand("Register a customer");
      bookButton.setActionCommand("Book a session");
      checkInButton.setActionCommand("Check in customer");
      viewScheduleButton.setActionCommand("View Schedule");
      
      registerButton.addActionListener(new ButtonClickListener()); 
      bookButton.addActionListener(new ButtonClickListener());
      checkInButton.addActionListener(new ButtonClickListener()); 
      viewScheduleButton.addActionListener(new ButtonClickListener()); 
      
      controlPanel.add(registerButton);
      controlPanel.add(bookButton);
      controlPanel.add(checkInButton);
      controlPanel.add(viewScheduleButton);
     
      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(mainStatusLabel);

      mainFrame.setVisible(true);
      bookingFrame.setVisible(false);
      registerFrame.setVisible(false);
   }
   
   // Sets up a window with user interface
   public void setup(JFrame frame, String windowName, String header){
       
      frame = new JFrame(windowName);
      frame.setSize(500,500);
      frame.setLayout(new GridLayout(4, 1));

      headerLabel = new JLabel(header,JLabel.CENTER );
      mainStatusLabel = new JLabel("...",JLabel.CENTER);             
      
      frame.addWindowListener(new WindowAdapter() {
          
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(5, 5));
     
      frame.add(headerLabel);
      frame.add(controlPanel);
      frame.add(mainStatusLabel);
      
      mainFrame.setVisible(false);
      frame.setVisible(true);
      
    }

   private void setVisibility(JFrame frame, boolean bool){
      frame.setVisible(bool); 
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
        
        String header = "REGISTER A CUSTOMER";
        String windowName = "Register a Customer - Provided by Invotech";
        setup(registerFrame, windowName, header);
        
        mainFrame.setVisible(false);
  
      JLabel  FirstNamelabel= new JLabel("Enter the First name: ", JLabel.CENTER);
      JLabel  LastNamelabel= new JLabel("Enter the Last name: ", JLabel.CENTER);
      JLabel  Emaillabel= new JLabel("Enter the Email: ", JLabel.CENTER);
      JLabel  TelephoneNolabel= new JLabel("Enter the Telephone number: ", JLabel.CENTER);
     
      final JTextField FirstName = new JTextField(6);
      final JTextField LastName = new JTextField(10);
      final JTextField Email = new JTextField(20);      
      final JTextField TelephoneNo = new JTextField(20);     

      JButton addButton = new JButton("Register Customer");
      addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String theFirstName = FirstName.getText();
            String theLastName = LastName.getText();
            String theEmail = Email.getText();
            String theTelephoneNo = TelephoneNo.getText();
            
            customerControllerConnection.register(connection, theFirstName, theLastName, theEmail, theTelephoneNo);
            
            String data = "A customer has been successfully registered: ";
			
            mainStatusLabel.setText(data);        
         }        
      }); 

      JButton backButton = new JButton("Back");
      backButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            
            setVisibility(registerFrame, false);
            setVisibility(mainFrame, true);
             
         }        
      });
      
      controlPanel.add(FirstNamelabel);
      controlPanel.add(FirstName);
      controlPanel.add(LastNamelabel);
      controlPanel.add(LastName);
      controlPanel.add(Emaillabel);
      controlPanel.add(Email);
      controlPanel.add(TelephoneNolabel); 
      controlPanel.add(TelephoneNo);
      controlPanel.add(addButton);
      controlPanel.add(backButton); 
    
        registerFrame.addWindowListener(new WindowAdapter() {
          
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
        });
    
    }  
       
    public void bookSession() {

      mainFrame.setVisible(false);
      
      String header = "BOOK A SESSION";
      String windowTitle = "Book a Session - Provided by InvoTech";
      setup(bookingFrame, windowTitle, header);
      
      
      JLabel  customerIDlabel= new JLabel("Enter the Customer's ID: ", JLabel.CENTER);
      JRadioButton withInstructorRadioButton = new JRadioButton("With Instructor");
      JRadioButton withoutInstructorRadioButton = new JRadioButton("Without Instructor");
      JLabel  sessionTypelabel= new JLabel("Enter the Session ID:  ", JLabel.CENTER);
      JLabel instructorlabel = new JLabel("Select Session Type: ", JLabel.CENTER);
      JLabel  emptyLabel1= new JLabel("    ", JLabel.CENTER);
      JLabel  emptyLabel2= new JLabel("    ", JLabel.CENTER);
      
      final JTextField customerIDText = new JTextField(6);
      final JTextField sessionIDText = new JTextField(10);

      JButton addButton = new JButton("Book Session");
      addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String theCustomerID = customerIDText.getText();
            String theSessionID = sessionIDText.getText();

            int num = Integer.parseInt(theCustomerID);
            int num2 = Integer.parseInt(theSessionID);
            
            bookingControlerConnection.book(connection, num, num2);
            
            String data = "A session has been booked for customer: " + customerIDText.getText();
            data += ", for session number: " + sessionIDText.getText();
            
            mainStatusLabel.setText(data);        
         }        
      });
      
              
      JButton checkCustomerButton = new JButton("Check Customer ID");
      checkCustomerButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             
            String theCustomerID = customerIDText.getText();
            String data;
             
            boolean isACustomer = customerControllerConnection.checkCustomerID(connection, theCustomerID);
            
            if(isACustomer == true) {
                
                data = "Customer is registered";
            }
            else {
                
                data = "Customer is not registered";
            }
            
            
            mainStatusLabel.setText(data);        
         }        
      });
      
      JButton viewButton = new JButton("View Bookings");
      viewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
                      
             try {
                 bookingControlerConnection.viewAll(connection);
             } catch (SQLException ex) {
                 Logger.getLogger(SlopeOperatorUI.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            String data = "Showing all bookings...";
            
            mainStatusLabel.setText(data);        
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
      controlPanel.add(customerIDText);
      controlPanel.add(checkCustomerButton);
      controlPanel.add(instructorlabel);
      controlPanel.add(withInstructorRadioButton);
      controlPanel.add(withoutInstructorRadioButton);
      controlPanel.add(sessionTypelabel);
      controlPanel.add(sessionIDText);
      controlPanel.add(emptyLabel1);
      controlPanel.add(emptyLabel1);
      controlPanel.add(emptyLabel1);
      controlPanel.add(addButton);
      controlPanel.add(viewButton);
      controlPanel.add(backButton); 
    }  
    public void checkInCustomer() {
        
     mainFrame.setVisible(false);
      
      String header = "CHECK-IN CUSTOMER";
      String windowTitle = "Check in Customer - Provided by InvoTech";
      setup(checkInFrame, windowTitle, header);
      
      final JTextField searchCustomer = new JTextField(6);
      JLabel  customerIDlabel= new JLabel("Enter the Customer's ID: ", JLabel.CENTER);
      JButton searchButton = new JButton("Search");
      JButton backButton = new JButton("Back");
      backButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           
             setVisibility(mainFrame, true);
             setVisibility(bookingFrame, false);
         }        
      });
     
      controlPanel.add(customerIDlabel);
      controlPanel.add(searchButton);
      controlPanel.add(backButton); 
    }        
    
    public void viewSchedule() {
        
        String header = "VIEW SCHEDULE";
        String windowName = "View Schedule - Provided by Invotech";
        setup(viewScheduleFrame, windowName, header);
        
        viewScheduleFrame.addWindowListener(new WindowAdapter() {
          
        public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
        });
        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             
             setVisibility(mainFrame, true);
             setVisibility(viewScheduleFrame, false);
         }        
        });
          
        controlPanel.add(backButton); 
    }
}
