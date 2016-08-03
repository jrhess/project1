/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package database;

import java.io.*;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author jordanhess
 */
public class employee {
    public static String oracleID;
    public static String oraclePassword;
    public static String[][] texts = new String[1000][3];
    public static String[][] calls = new String[1000][3];
    public static String[][] internet = new String[1000][2];
    public static int textLength = 0;
    public static int callLength = 0;
    public static int internetLength = 0;
    
    public static void main(String[] args){

    }
    
    public static void runInterface(String id, String password) throws SQLException, IOException, ClassNotFoundException{
        oracleID = id;
        oraclePassword = password;
        
        System.out.println("Welcome to the Jog Wireless Employee interface");
        optionsPrompt();
        
    }
    public static void optionsPrompt() throws SQLException, IOException, ClassNotFoundException{ //main menu for customer once they login
    System.out.println("Please select one the following options:");
        
        System.out.println("[1] Look up customer information");
        System.out.println("[2] Look up customer usage");
        System.out.println("[3] Manage store inventory");
        System.out.println("[4] Exit");
        options();
        
    }
    
     public static void options() throws SQLException, IOException, ClassNotFoundException{ //main menu for employee once they login
        
        
        
        Scanner options = new Scanner(System.in);
        String menuChoice = options.nextLine();
        
        
        if(menuChoice.equals("1")){
           customerLookup();
        }
        if(menuChoice.equals("2")){
           customerUsage();
        }
        
        if(menuChoice.equals("3")){  
           manageInventory();
        }

        if(menuChoice.equals("4")){
            System.out.println("Remember, service with a smile!");
            System.exit(0);
        }
        else{
         
            optionsPrompt();
        }
    }
     
    public static void customerLookup() throws SQLException, IOException, ClassNotFoundException{
        
        System.out.println("Please select one the following options:");
        
        System.out.println("[1] Look up customer by phone number");
        System.out.println("[2] Look up customer by name");
        System.out.println("[3] Look up customer by address");
        System.out.println("[4] Look up customer by CID");
        System.out.println("[5] Look up customer by AID");
        System.out.println("[6] Look up account phone numbers by AID");
        System.out.println("[7] Return to previous menu");
        
        
        Scanner options = new Scanner(System.in);
        String menuChoice = options.nextLine();
        
        if(menuChoice.equals("1")){
            while (true){
                System.out.println("enter a phone number to look in the format of XXXXXXXXXX: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
                
                if (pLookup.matches("[0-9]+") && pLookup.length() ==  10) {
                 //   System.out.println("passed");
            
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from customer C, account A, owns O, phone_number P where C.CID = O.CID and O.AID = A.AID and A.AID = P.NID and P.Numbers =" + pLookup;
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such customer exists, try again");
                    customerLookup();
                }
                else {
                    do {
                        System.out.println("Customer Information:");
                        System.out.println("Primary Number: " + result.getString("PNUMBER"));
                        System.out.println("Name: " + result.getString("NAME"));
                        System.out.println("Address: " +result.getString("ADDRESS"));
                        System.out.println("Account type: " +result.getString("ATYPE"));
                        System.out.println("Account ID: " +result.getString("AID"));
                        System.out.println("Customer ID: " +result.getString("CID"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                 }else{
                    System.out.println("Bad input");
                }
            }
        }
        if(menuChoice.equals("2")){
           while (true){
                System.out.println("Enter a name to look up: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
            
                if(pLookup.matches("[a-zA-Z]+")){
                
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from customer C, account A, owns O where C.CID = O.CID and O.AID = A.AID and C.name = '" + pLookup + "'";
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such customer exists, try again");
                    customerLookup();
                }
                else {
                    do {
                        System.out.println("Customer Information:");
                       System.out.println("Primary Number: " + result.getString("PNUMBER"));
                       System.out.println("Name: " +result.getString("NAME")); 
                       System.out.println("Address: " +result.getString("ADDRESS"));
                        System.out.println("Account type: " +result.getString("ATYPE"));
                        System.out.println("Account ID: " +result.getString("AID"));
                        System.out.println("Customer ID: " +result.getString("CID"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                }else{
                    System.out.println("Bad input");
                }
            }
        }
        
        if(menuChoice.equals("3")){  
           while (true){
                System.out.println("Enter an address to look up: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
                
                 if(pLookup.matches("[a-zA-Z]+")){
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from customer C, account A, owns O where C.CID = O.CID and O.AID = A.AID and C.address =  '" + pLookup + "'";
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such customer exists, try again");
                    customerLookup();
                }
                else {
                    do {
                        System.out.println("Customer Information:");
                       System.out.println("Primary Number: " + result.getString("PNUMBER"));
                        System.out.println("Name: " +result.getString("NAME"));
                        System.out.println("Address: " +result.getString("ADDRESS"));
                        System.out.println("Account type: " +result.getString("ATYPE"));
                        System.out.println("Account ID: " +result.getString("AID"));
                        System.out.println("Customer ID: " +result.getString("CID"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                 }else{
                    System.out.println("Bad input");
                }

            }
        }
        if(menuChoice.equals("4")){  
           while (true){
                System.out.println("Enter an CID to look up: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
            if (pLookup.matches("[0-9]+") && pLookup.length() ==  6) {
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from customer C, account A, owns O where C.CID = O.CID and O.AID = A.AID and C.CID ='" + pLookup + "'";
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such customer exists, try again");
                    customerLookup();
                }
                else {
                    do {
                        System.out.println("Customer Information:");
                       System.out.println("Primary Number: " + result.getString("PNUMBER"));
                        System.out.println("Name: " +result.getString("NAME"));
                        System.out.println("Address: " +result.getString("ADDRESS"));
                        System.out.println("Account type: " +result.getString("ATYPE"));
                        System.out.println("Account ID: " +result.getString("AID"));
                        System.out.println("Customer ID: " +result.getString("CID"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                }else{
                    System.out.println("Bad input");
                }

            }
        }
        if(menuChoice.equals("5")){  
           while (true){
                System.out.println("Enter an AID to look up: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
            
                if (pLookup.matches("[0-9]+") && pLookup.length() ==  6) {
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from customer C, account A, owns O where C.CID = O.CID and O.AID = A.AID and A.AID = '" + pLookup + "'";
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such customer exists, try again");
                    customerLookup();
                }
                else {
                    do {
                        System.out.println("Customer Information:");
                       System.out.println("Primary Number: " + result.getString("PNUMBER"));
                        System.out.println("Name: " +result.getString("NAME"));
                        System.out.println("Address: " +result.getString("ADDRESS"));
                        System.out.println("Account type: " +result.getString("ATYPE"));
                        System.out.println("Account ID: " +result.getString("AID"));
                        System.out.println("Customer ID: " +result.getString("CID"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                 }else{
                    System.out.println("Bad input");
                }

            }
        }
        if(menuChoice.equals("6")){  
           while (true){
                System.out.println("Enter an AID to find of the phone numbers linked to it: ");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
            
                if (pLookup.matches("[0-9]+") && pLookup.length() ==  6) {
                    
                Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                      oraclePassword);//change this, cant have password out in the open

                Statement s=con.createStatement();
                String q1;
                ResultSet result;
                q1 = "select * from phone_number where NID = '" + pLookup + "'";
                result = s.executeQuery(q1);

                if (!result.next()){
                    System.out.println ("No such phone numbers exist, try again");
                    customerLookup();
                }
                else {
                    System.out.println("Phone number(s):");
                    do {
                        
                       System.out.println(result.getString("NUMBERS"));

                         } while (result.next());
                     }
                 s.close();
                 con.close();
                 System.out.println(); 
                 customerLookup();
                }else{
                    System.out.println("Bad input");
                }
            }
        }

        if(menuChoice.equals("7")){
            optionsPrompt();
        }
        else{
         
           System.out.println("Unkown input");
           customerLookup();
        }
    
    }
    
    public static void customerUsage() throws SQLException, IOException, ClassNotFoundException{
        while (true){
                System.out.println("enter a phone number to look in the format of XXXXXXXXXX to find it's usage");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
                
                if (pLookup.matches("[0-9]+") && pLookup.length() ==  10) {  
                    long currentNumber = Long.parseLong(pLookup);
                    getUsage(currentNumber);
                }
    
        }
      
    }
    
    public static void getUsage(long currentNumber) throws SQLException, IOException, ClassNotFoundException{
     
        getCalls(currentNumber); 
        getInternet(currentNumber);
        getTexts(currentNumber);
        displayUsage();
        
    }
    public static void displayUsage() throws SQLException, IOException, ClassNotFoundException{
       System.out.println("Texts: ");
       System.out.println("Sent to:   Date sent:   Bytes:");
            for(int i = 0; i < textLength; i++){
                
                System.out.print(texts[i][0] + " ");
                System.out.print(texts[i][1] + " ");
                System.out.print(texts[i][2] + " ");
                System.out.println();
            }
        System.out.println();
        System.out.println("Calls: ");
        System.out.println("Sent to:   Start time:   End time:");
        for(int i = 0; i < callLength; i++){
                
                System.out.print(calls[i][0] + " ");
                System.out.print(calls[i][1] + " ");
                System.out.print(calls[i][2] + " ");
                System.out.println();
            }
        
        System.out.println();
        System.out.println("Internet Usage: ");
        System.out.println("Date:   Bytes: ");
        for(int i = 0; i < internetLength; i++){
                
                System.out.print(internet[i][1] + " ");
                System.out.print(internet[i][0] + " ");
                System.out.println();
            }
        
        
        System.out.println();
            System.out.println("Please select one the following options:");
            System.out.println("[1] return to previous menu");
            while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                if(back.equals("1")) {
                    //System.out.println("Please select from the following options: Account Information, Usage, Purchase Phone, or Billing Plan");
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
       
   }
    public static void getCalls(long currentNumber) throws SQLException, IOException, ClassNotFoundException{

        
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from CALL where SENDINGNUM = (select numbers from phone_number where numbers = " + currentNumber + ")"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){
            //System.out.println ("No call usage");
           // getInternet();
            
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
            callLength = 0;
	    do {

                calls[callLength][0] = result.getString("RECEIVINGNUM");
                calls[callLength][1] = result.getString("STARTTIME");
                calls[callLength][2] = result.getString("ENDTIME");
              

                callLength++;
		} while (result.next());
	    }
	s.close();
	con.close();    
    }
    public static void getInternet(long currentNumber) throws SQLException, IOException, ClassNotFoundException{
            Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from INTERNET where SENDINGNUM = (select numbers from phone_number where numbers = " + currentNumber + ")"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){
            //System.out.println ("No Internt usage");
           // getTexts();
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
            internetLength = 0;
	    do {

                internet[internetLength][0] = result.getString("DATEUSED");
                internet[internetLength][1] = result.getString("BYTES");
                
                internetLength++;
                
		} while (result.next());
	    }
	s.close();
	con.close();     
    } 
    public static void getTexts(long currentNumber) throws SQLException, IOException, ClassNotFoundException{
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from TEXT where SENDINGNUM = (select numbers from phone_number where numbers = " + currentNumber + ")"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){
           
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
            textLength = 0;
	    do {

                texts[textLength][0] = result.getString("RECEIVINGNUM");
                texts[textLength][1] = result.getString("SENDTIME");
                texts[textLength][2] = result.getString("BYTES");
                

                textLength++;
		} while (result.next());
	    }
	s.close();
	con.close();
    }
    public static void manageInventory() throws SQLException, IOException, ClassNotFoundException{
        
          System.out.println("Choose a store from below:");
        
        
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from physicalstore"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){

        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
           // callLength = 0;
           System.out.println("Inventories");
           System.out.println("Store ID | Apple | Samsung | HTC | LG");
	    do {

               System.out.print(result.getString("STOREID") + "     ");
               System.out.print(result.getString("APPLE")+ "     ");
               System.out.print(result.getString("SAMSUNG")+ "      ");
               System.out.print(result.getString("HTC")+ "     ");
               System.out.print(result.getString("LG")+ "     ");
               System.out.println();
               
		} while (result.next());
	    }
	s.close();
	con.close();  
        
        
        while (true){
                System.out.println("Enter a store ID number to submit restock request");
                Scanner pnumber = new Scanner(System.in);
                String pLookup = pnumber.nextLine();
                
                if (pLookup.matches("[0-9]+") && pLookup.length() ==  6) {  
                    int storeID = Integer.parseInt(pLookup);
                    
                    try {
                            System.out.println("poop");
                            Connection con2=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con2=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s2=con2.createStatement();
                            String q2;
                            ResultSet result1;
                            int i2;
                           // System.out.println("poop1");
                            q2 = "UPDATE physicalstore SET LG = 300, APPLE = 300, SAMSUNG = 300, HTC = 300 WHERE STOREID =" + storeID; //into phone_number
                            //System.out.println("poop2");
                            i2 = s2.executeUpdate(q2);
                            s2.close();
                           // System.out.println("poop3");
                            con2.close();
                          //  System.out.println("poop4");
                        } catch(Exception e){ 
                            System.out.println("exception");
                            e.printStackTrace();}
                    
                    
                    System.out.println("Restock request for store: " + storeID + " has been submitted");
                    optionsPrompt();
                }else{
                    System.out.println("Unknown input");
                }
    
        }
        
    }
    
}
