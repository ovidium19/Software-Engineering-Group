/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.io.*;
public class Menu {
    private String theTitle = null;
    private String[] theOptions;//a set of menu items
    private String thePrompt = "Please enter your choice: ";
    private char minimumOption;//starting menu item
    private char maximumOption;//finishing menu item
    private BufferedReader keyboard = new  BufferedReader(new InputStreamReader(System.in));
    
    /*the constructor method which assigns the given values to 
    the attributes: theTitle, theOptions and thePrompt  
     */
    public Menu(String title, String[] options, String prompt){  
        if (title.length()>0){
            theTitle = title; //assign the given value in title to attribute, theTitle
        }                 
        theOptions = options;              
        if (prompt.length()>0){
            thePrompt = prompt;
        }   
    }       
    //This method is to display menu options and get valid menu option from the user
    public char offerMenu(){  
        char validResponse;                
        showMenu();              
        validResponse = getValidChoice(); 
        return validResponse;        
    }    
    private void showMenu(){  
        char thisOption = 'A';        
        setMinimumOption(thisOption);
        showTitle();        
        for (int i=0; i<theOptions.length; i++){
            showMenuLine(thisOption, i);
            thisOption++;
        }        
        setMaximumOption(--thisOption);
    }    
    protected void showTitle(){  
        if (theTitle != null){
            System.out.println ("\t" + theTitle + "\n");
        }  
    }    
    protected void showMenuLine(char menuLabel, int menuText){         
        System.out.println (menuLabel +".   " + theOptions[menuText]);          
    }    
    protected void setMinimumOption(char setTo){         
        minimumOption = setTo;          
    }    
    protected void setMaximumOption(char setTo){         
        maximumOption = setTo;          
    }    
    protected char getValidChoice(){         
        String fromKeyboard = null; 
        char response = ' ';
        boolean invalidResponse = true;        
        System.out.println (thePrompt + " ");
        System.out.flush();//to flush output stream to write data out to socket        
        while (invalidResponse){
            try {
                fromKeyboard = keyboard.readLine();                
                if (fromKeyboard.length()>0){
                    response = fromKeyboard.charAt(0);                    
                }
                else {
                    response = ' ';
                }                    
            }
            catch (java.io.IOException exception){                
            }      





      
            response = Character.toUpperCase(response);            
            invalidResponse = ((response < minimumOption) || 
                    (response > maximumOption));            
            if (invalidResponse){
                System.out.println ("Please enter a response between " + 
                        minimumOption + " and " + maximumOption + ".");
                System.out.println (thePrompt + " ");
                System.out.flush();
            }
        }
        return response;
    }
}    
