


//package database;

import java.io.*;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class customer {
    
    public static int CID = 0;
    public static String AID = "";
    public static String NAME = "";
    public static String ADDRESS = "";
    public static String PNUMBER = "";
    public static String ATYPE = "";
    public static boolean backCheck = false;
    public static String oracleID;
    public static String oraclePassword;
    public static String[][] texts = new String[1000][3];
    public static String[][] calls = new String[1000][3];
    public static String[][] internet = new String[1000][2];
    public static String[][] totalTexts = new String[1000][3];
    public static String[][] totalCalls = new String[1000][3];
    public static String[][] totalInternet = new String[1000][2];
    public static String[][] phones = new String[1000][3];
    public static int textLength = 0;
    public static int callLength = 0;
    public static int internetLength = 0;
    public static int totalTextLength = 0;
    public static int totalCallLength = 0;
    public static int totalInternetLength = 0;
    public static int phoneLength = 0;
    public static String btype = "";
    public static String trate = "";
    public static String prate = "";
    public static String irate = "";
    public static String tlimit = "";
    public static String plimit = "";
    public static String ilimit = "";
    public static long currentNumber = 0;
    
    
    
    public static void main(String[] args){
        // NOTHING :)  ///
    }
    
    public static void runInterface(String id, String password) throws IOException, SQLException, ClassNotFoundException{ //driver of interface
       oracleID = id;
        oraclePassword = password;
        
        if(validInput()){
           customerLogin(); //get all of the Customer info
           //System.out.println("Welcome " + NAME + ", which the following accounts:");
           //System.out.println(AID);
           System.out.println("would you like to access?");
           accountChoice();
           System.out.print("Welcome " + NAME + ", ");
           optionsPrompt();
           
       }
        
    }
    
    public static boolean validInput(){
        
        Scanner sc=new Scanner(System.in);
        CID = 0;
        try{
            System.out.println("Please enter your enter Customer number");
            CID =sc.nextInt();
        }
        catch(InputMismatchException exception){
            System.out.println("Input was not between 0 and 999999");
            validInput();
        }

        if(CID > 999999 || CID < 0){//check that id is within range
            System.out.println("Input was not between 0 and 999999");
            validInput();
        }else{
            return true;
        }
        return false;
    }
    public static void customerLogin()
    throws SQLException, IOException, java.lang.ClassNotFoundException{
        
        //int[] AIDs = new int[10]
        
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from customer C, owns O where C.CID = O.CID and C.CID =" + CID; //add to this to get more info
	result = s.executeQuery(q1);
      
      	if (!result.next()){
            System.out.println ("No such customer exists, try again");
            runInterface(oracleID, oraclePassword);
        }
	else {
            System.out.println("Which of the following accounts would you like to access?");
	    do {

	        
                System.out.println(result.getString("AID") + " ");
                NAME = result.getString("NAME");
                 ADDRESS = result.getString("ADDRESS");
                 
                 AID = result.getString("AID");
		} while (result.next());
	    }
        
	s.close();
	con.close();
        
    }    
    public static void accountChoice()
    throws SQLException, IOException, java.lang.ClassNotFoundException{
        
        Scanner aidChoice = new Scanner(System.in);
        String a = aidChoice.nextLine();
        
        boolean check = true;
        while(check){
        
        if (a.matches("[0-9]+") && a.length() == 6) {
       
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from account where AID =" + a; 
	result = s.executeQuery(q1);
      	if (!result.next()){
            System.out.println ("No such account exists, try again");
            runInterface(oracleID, oraclePassword);
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
	    do {

	      
                PNUMBER = result.getString("PNUMBER");
		ATYPE = result.getString("ATYPE");
                //add more info here
                
                
		} while (result.next());
	    }
        
	s.close();
	con.close();
            check = false;
            System.out.print("Welcome " + NAME + ", ");
            optionsPrompt();
            
            }else{
                System.out.println("Unknown input");
                accountChoice();
            }
        
        }
        
       
    }
    
    
    public static void getUsage() throws SQLException, IOException, ClassNotFoundException{
     
        getCalls(); 
        getInternet();
        getTexts();
    }
    public static void getCalls() throws SQLException, IOException, ClassNotFoundException{

        
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
                
                totalCalls[totalCallLength][0] = result.getString("RECEIVINGNUM");
                totalCalls[totalCallLength][1] = result.getString("STARTTIME");
                totalCalls[totalCallLength][2] = result.getString("ENDTIME");

                callLength++;
                totalCallLength++;
		} while (result.next());
	    }
	s.close();
	con.close();    
    }
    public static void getInternet() throws SQLException, IOException, ClassNotFoundException{
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
                
                totalInternet[totalInternetLength][0] = result.getString("DATEUSED");
                totalInternet[totalInternetLength][1] = result.getString("BYTES");

                internetLength++;
                totalInternetLength++;
                
		} while (result.next());
	    }
	s.close();
	con.close();     
    } 
    public static void getTexts() throws SQLException, IOException, ClassNotFoundException{
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
                
                totalTexts[totalTextLength][0] = result.getString("RECEIVINGNUM");
                totalTexts[totalTextLength][1] = result.getString("SENDTIME");
                totalTexts[totalTextLength][2] = result.getString("BYTES");

                textLength++;
                totalTextLength++;
		} while (result.next());
	    }
	s.close();
	con.close();
    }
    public static void getBillingplan() throws SQLException, IOException, ClassNotFoundException{
         Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from billing where BTYPE = (select Atype from account where aid = " + AID + ")"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){
            //System.out.println ("No Internt usage");
            //optionsPrompt();
        }
	else {
	  
	    do {

                btype = result.getString("btype");
                trate = result.getString("trate");
                prate = result.getString("prate");
                irate = result.getString("irate");
                tlimit = result.getString("tlimit");
                plimit = result.getString("plimit");
                ilimit = result.getString("ilimit");
                
		} while (result.next());
	    }
	s.close();
	con.close();     
        
        
    }
    public static void getPhones() throws SQLException{
       // numberChoice(); 
        Connection con1 = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open
        
        int counter = 0;
        Statement s1=con1.createStatement();
	String q2;
	ResultSet result1;
	q2 = "select numbers from phone_number where nid = " + AID; 
        result1 = s1.executeQuery(q2);
      	if (!result1.next()){ 
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
            
	    do {
               // System.out.println(result1.getString("NUMBERS"));
                phones[counter][0] = result1.getString("NUMBERS");
                //System.out.println("adding: " + phones[counter][0]);
                counter++;   
                
		} while (result1.next());
	    }
        //counter++;
	s1.close();
	con1.close();
        
       // System.out.println("counter: " + counter);
  
        phoneLength = 0;
        counter++;
        
        for(int i = 0; i < counter; i++){
            Connection con = DriverManager.getConnection
                ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
                 oraclePassword);//change this, cant have password out in the open
       //      System.out.println("loop: " +  phones[i][0]);
            Statement s=con.createStatement();
            String q1;
            ResultSet result;
            q1 = "select * from phone where PHONENUMBER =" + phones[i][0]; 
            result = s.executeQuery(q1);
            //System.out.println("adding phone");

            if (!result.next()){

            }
            else {

                 //   System.out.println(result.getString("MANUFACTURE"));
                    phones[i][0] = result.getString("PHONENUMBER");
                    phones[i][1] = result.getString("MANUFACTURE");
                    phones[i][2] = result.getString("MODEL");

                    phoneLength++;


                }
            s.close();
            con.close();     
        }
        
    }
   
    
    public static void optionsPrompt() throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Please select one the following options:");
        System.out.println("[1] Account Information");
        System.out.println("[2] Usage");
        System.out.println("[3] Manage Phones");
        System.out.println("[4] Billing Plan");
        System.out.println("[5] Payment");
        System.out.println("[6] Add Account");
        System.out.println("[7] Exit");
        options();
        
    }
    public static void options() throws SQLException, IOException, ClassNotFoundException{ //main menu for customer once they login
        
        
        
        Scanner options = new Scanner(System.in);
        String menuChoice = options.nextLine();
        
        //if(menuChoice.equals("Account Information") || menuChoice.equals("account information") || menuChoice.equals("account Information") || menuChoice.equals("Account information")){
        
        if(menuChoice.equals("1")){
           displayAccountInformation();
        }
        //if(menuChoice.equals("Usage") || menuChoice.equals("usage")){
        if(menuChoice.equals("2")){
            getPhones();
            numberChoice();
            getUsage();
           displayUsage(); 
        }
        
        //if(menuChoice.equals("Purchase Phone") || menuChoice.equals("purchase phone") || menuChoice.equals("Purchase phone") || menuChoice.equals("purchase Phone")){
        if(menuChoice.equals("3")){  
            displayPhones(); 
        }
        
        //if(menuChoice.equals("Billing") || menuChoice.equals("billing phone")){
        if(menuChoice.equals("4")){
          getBillingplan();
          displayBillingplan();
            
        }
        if(menuChoice.equals("5")){
            payment();

        }
        if(menuChoice.equals("6")){
            addAccount();

        }
        if(menuChoice.equals("7")){
            System.out.println("Thank you for choosing Jog Wireless!");
            System.exit(0);
        }
        else{
            //System.out.println("Please select from the following options: Account Information, Usage, Purchase Phone, or Billing Plan");
                        System.out.println("Unkown input");
                        optionsPrompt();

        }
    }
    
    public static void numberChoice() throws SQLException{

        System.out.println("Select the phone number you would like view the usage of:" );
        System.out.println("Phones owned by account: " + AID);
        System.out.println("Phone number:   Manufacturer:   Model:");
            int i = 0;
            
            for(i = 0; i < phoneLength; i++){
                
                System.out.println();
                System.out.print("[" + (i + 1) + "] " + phones[i][0] + " ");
                System.out.printf("%8s",phones[i][1] + " ");
                System.out.printf("%16s",phones[i][2] + " ");
                
            }
            System.out.println();
            boolean finished = true;
            while (finished){
               
                Scanner S = new Scanner(System.in);
                String back = S.next();
                
                if(back.matches("^?\\d+$")){
                    int select = Integer.parseInt(back);
                    System.out.println(select);
                    //select = select - 1;
                    if(select > 0 && select < (phoneLength + 1)){
                        //System.out.println(phones[select][0]);
                        long newLong = Long.parseLong(phones[select - 1][0]);
                          currentNumber = newLong;
                           System.out.println(newLong + " has been selected");
                           System.out.println();
                           finished = false;
                        
                    }else{
                        System.out.println("Please enter an interger with the range of 1 - " + phoneLength);
                    }
                    
                }else{
                    
                    System.out.println("Please enter an integer");
                }
              
               
                
               
            
            }
        
    }
    
    public static void displayAccountInformation() throws IOException, SQLException, ClassNotFoundException{ //has name, address, account type, pnumber, usage
            
            System.out.println();
            System.out.println("Customer " + NAME + "'s account information:");
            System.out.println("Customer ID number: " + CID);
            System.out.println("Address: " + ADDRESS);
            System.out.println("Account type: " + ATYPE);
            
            String temp = PNUMBER;
            PNUMBER = PNUMBER.format("%s-%s-%s", PNUMBER.substring(0, 3), PNUMBER.substring(3, 6), PNUMBER.substring(6, 10));
            System.out.println("Primary number: " + PNUMBER);
            PNUMBER = temp;
    
            System.out.println();
            System.out.println("Please select one the following options:");
            System.out.println("[1] Edit information");
            System.out.println("[2] return to previous menu");
            while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                if(back.equals("1")){
                    alterAccountInformation();
                }
                if(back.equals("2")) {
                   // System.out.println("Please select from the following options: Account Information, Usage, Purchase Phone, or Billing Plan");
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
            
    }
    
    public static void alterAccountInformation()
        throws SQLException, IOException, ClassNotFoundException{
        
        System.out.println("Please select one the following options:");
        System.out.println("[1] Edit name");
        System.out.println("[2] Edit address");
        System.out.println("[3] return to previous menu");
        
        while (true){
            Scanner alterOptions = new Scanner(System.in);
            String alterChoice = alterOptions.nextLine();

            //if(menuChoice.equals("Account Information") || menuChoice.equals("account information") || menuChoice.equals("account Information") || menuChoice.equals("Account information")){

            if(alterChoice.equals("1")){
                System.out.println("Enter new name (must be less than 20 characters)");
                String newName;

                while (true){
                    Scanner nameS = new Scanner(System.in);
                    newName = nameS.nextLine();

                    if(newName.length() < 20){//check that id is within range
                           
                        
                        try {
                            Connection con=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s=con.createStatement();
                            String q;
                            ResultSet result;
                            int i;
                            q = "UPDATE Customer SET NAME = '" + newName + "' WHERE CID = " + CID;
                            i = s.executeUpdate(q);
                            s.close();
                            con.close();
                        } catch(Exception e){e.printStackTrace();}

                        NAME = newName;//update global value
                        System.out.println("Name changed to: " + newName);
                        alterAccountInformation();

                    }else{
                     System.out.println("input too long");
                    }

                }

            }
            
            if(alterChoice.equals("2")){
                System.out.println("Enter new address (must be less than 40 characters)");
                 String newAddress;
                while (true){
                    Scanner addresss = new Scanner(System.in);
                    newAddress = addresss.nextLine();

                    if(newAddress.length() < 40){//check that id is within range
                           
                        
                        try {
                            Connection con=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s=con.createStatement();
                            String q;
                            ResultSet result;
                            int i;
                            q = "UPDATE Customer SET ADDRESS = '" + newAddress + "' WHERE CID = " + CID;
                            i = s.executeUpdate(q);
                            s.close();
                            con.close();
                        } catch(Exception e){e.printStackTrace();}

                        ADDRESS = newAddress;//update global value
                        System.out.println("Address changed to: " + newAddress);
                        alterAccountInformation();

                    }else{
                     System.out.println("input too long");
                    }

                }
            }
            if(alterChoice.equals("3")){
                displayAccountInformation();
            }else{
                 System.out.println("Unknown input");
            }
        }
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
    
    public static void displayBillingplan() throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Current billing plan rates: ");
        System.out.println("Plan type:  Text rate:  Phone rate:  Internet rate:  Text limit:  Phone limit:  Internet limit:");
        System.out.print(btype + "      ");
        System.out.print(trate + "         ");
        System.out.print(prate + "           ");
        System.out.print(irate + "             ");
        System.out.print(tlimit + "          ");
        System.out.print(plimit + "          ");
        System.out.print(ilimit + "      ");
        System.out.println();
        System.out.println();
            
        
        System.out.println("Please select one the following options:");
        System.out.println("[1] Change blling plans");
        System.out.println("[2] return to previous menu");
             while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                if(back.equals("1")){
                    alterBillingPlan();
                }
                if(back.equals("2")) {
                   // System.out.println("Please select from the following options: Account Information, Usage, Purchase Phone, or Billing Plan");
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
    }
    
    public static void alterBillingPlan() throws SQLException, IOException, ClassNotFoundException{
        
        System.out.println("Jog Wireless offers three billing plans: Individaul, Family, and Business. Below are the rates and limits for each");
        
        Connection con = DriverManager.getConnection
	    ("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", oracleID,
	     oraclePassword);//change this, cant have password out in the open

	Statement s=con.createStatement();
	String q1;
	ResultSet result;
	q1 = "select * from billing"; 
	result = s.executeQuery(q1);
      
      	if (!result.next()){
            
        }
	else {
	    //  System.out.println("Transcript for student " + result.getString("ID") + result.getString("name"));
            System.out.println("Plan type:  Text rate:  Phone rate:  Internet rate:  Text limit:  Phone limit:  Internet limit:");
	    do {
                System.out.println();
                System.out.println(result.getString("btype") + ":");
                System.out.printf("%15s",result.getString("trate"));
                System.out.printf("%11s",result.getString("prate"));
                System.out.printf("%14s",result.getString("irate"));
                System.out.printf("%17s",result.getString("tlimit"));
                System.out.printf("%13s",result.getString("plimit"));
                System.out.printf("%14s",result.getString("ilimit"));
                System.out.println();
		} while (result.next());
	    }
	s.close();
	con.close(); 
        
        System.out.println("Please select one the following options:");
            System.out.println("[1] Change blling plan to individual");
            System.out.println("[2] Change blling plan to family");
            System.out.println("[3] Change blling plan to business");
            System.out.println("[4] return to previous menu");;
            while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                
                if(back.equals("1")){
                    //System.out.println("1");
                   actuallyAlterBilling(1);
                }
                if(back.equals("2")){
                    actuallyAlterBilling(2);
                }
                if(back.equals("3")){
                    actuallyAlterBilling(3);
                }
                if(back.equals("4")) {
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
    }
    
    public static void actuallyAlterBilling(int newPlan) throws SQLException, IOException, ClassNotFoundException{
         
        if(newPlan == 1){
            ATYPE = "individual";
        }
         if(newPlan == 2){
            ATYPE = "family";
        }
          if(newPlan == 3){
            ATYPE = "business";
        }
        try {
                            Connection con1=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con1=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s1=con1.createStatement();
                            String q2;
                            ResultSet result1;
                            int i;
                            q2 = "UPDATE ACCOUNT SET ATYPE = '" + ATYPE + "' WHERE AID = " + AID;
                            i = s1.executeUpdate(q2);
                            s1.close();
                            con1.close();
                        } catch(Exception e){e.printStackTrace();}
                
                 System.out.println("You are now under the: " + ATYPE + " billing plan");
                 getBillingplan();
                 displayBillingplan();
    }

    public static void displayPhones() throws SQLException, IOException, ClassNotFoundException{
        getPhones();
        System.out.println("Phones owned by account: " + AID);
        System.out.println("Phone number:   Manufacturer:   Model:");
        
        
        
            for(int i = 0; i < phoneLength; i++){
                
                System.out.println();
                
                System.out.print(phones[i][0] + " ");
                System.out.printf("%8s",phones[i][1] + " ");
                System.out.printf("%16s",phones[i][2] + " ");
                
            }
            System.out.println();
            System.out.println();
            System.out.println("Please select one the following options:");
            System.out.println("[1] Purchase a new phone");
            System.out.println("[2] Remove a phone");
            System.out.println("[3] return to previous menu");
            while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                
                if(back.equals("1")){
                    addPhone();
                }
                if(back.equals("2")){
                   removePhone();
                }
                if(back.equals("3")) {
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
        
    }
    
    public static void addPhone() throws SQLException, IOException, ClassNotFoundException{
        System.out.println("Please select one the following options:");
        System.out.println("[1] Purchase phone online");
        System.out.println("[2] Purchase phone in a store");
        System.out.println("[3] return to previous menu");
        
        while (true){
                Scanner S = new Scanner(System.in);
                String back = S.next();
                
                if(back.equals("1")){
                    System.out.println("Please select one the following manufactures:");
                    System.out.println("[1] LG");
                    System.out.println("[2] HTC");
                    System.out.println("[3] Samsung");
                    System.out.println("[4] Apple");
                    System.out.println("[5] return to previous menu");
                    
                    while (true){
                        Scanner S1 = new Scanner(System.in);
                        String phoneChoice = S1.next();
                        
                        if(phoneChoice.equals("2")){
                            while (true){
                                System.out.println("Please select one the following models:");
                                System.out.println("[1] One M9");
                                System.out.println("[2] One A9");
                                System.out.println("[3] return to previous menu");
                                
                                
                                 while (true){
                                    Scanner S2 = new Scanner(System.in);
                                    String HTCPhoneChoice = S2.next();

                                    if(HTCPhoneChoice.equals("1")){
                                        actuallyAddPhone("HTC", "One M9");   
                                    }
                                    if(HTCPhoneChoice.equals("2")){
                                       actuallyAddPhone("HTC", "One A9");
                                    }                                    
                                    if(HTCPhoneChoice.equals("3")) {
                                        addPhone();
                                    }else{
                                     System.out.println("Unknown input");
                                    }

                                }

                            }
                        }
                        if(phoneChoice.equals("1")){
                           while (true){
                                System.out.println("Please select one the following models:");
                                System.out.println("[1] V10");
                                System.out.println("[2] G5");
                                System.out.println("[3] return to previous menu");
                                
                                
                                 while (true){
                                    Scanner S2 = new Scanner(System.in);
                                    String LGPhoneChoice = S2.next();

                                    if(LGPhoneChoice.equals("1")){
                                        actuallyAddPhone("LG", "V10");   
                                    }
                                    if(LGPhoneChoice.equals("2")){
                                       actuallyAddPhone("LG", "G5");
                                    }                                 
                                    
                                    if(LGPhoneChoice.equals("3")) {
                                        addPhone();
                                    }else{
                                     System.out.println("Unknown input");
                                    }

                                }

                            }
                        }
                        if(phoneChoice.equals("3")){
                           while (true){
                                System.out.println("Please select one the following models:");
                                System.out.println("[1] Galaxy S7");
                                System.out.println("[2] Galaxy S6");
                                System.out.println("[3] return to previous menu");
                                
                                
                                 while (true){
                                    Scanner S2 = new Scanner(System.in);
                                    String samsungPhoneChoice = S2.next();

                                    if(samsungPhoneChoice.equals("1")){
                                        actuallyAddPhone("Samsung", "Galaxy S7");   
                                    }
                                    if(samsungPhoneChoice.equals("2")){
                                       actuallyAddPhone("Samsung", "Galaxy S6");
                                    }                                    
                                   
                                    if(samsungPhoneChoice.equals("3")) {
                                        addPhone();
                                    }else{
                                     System.out.println("Unknown input");
                                    }

                                }

                            }
                        }
                        if(phoneChoice.equals("4")){
                           while (true){
                                System.out.println("Please select one the following models:");
                                System.out.println("[1] iPhone 6s");
                                System.out.println("[2] iPhone 6");
                                System.out.println("[3] iPhone 5s");
                                System.out.println("[4] iPhone 5");
                                System.out.println("[5] return to previous menu");
                                
                                
                                 while (true){
                                    Scanner S2 = new Scanner(System.in);
                                    String applePhoneChoice = S2.next();

                                    if(applePhoneChoice.equals("1")){
                                        actuallyAddPhone("Apple", "iPhone 6s");   
                                    }
                                    if(applePhoneChoice.equals("2")){
                                       actuallyAddPhone("Apple", "iPhone 6");
                                    }                                    
                                    if(applePhoneChoice.equals("3")){
                                       actuallyAddPhone("Apple", "iPhone 5s");
                                    }                                  
                                    if(applePhoneChoice.equals("4")){
                                       actuallyAddPhone("Apple", "iPhone 5");
                                    }
                                    if(applePhoneChoice.equals("5")) {
                                        addPhone();
                                    }else{
                                     System.out.println("Unknown input");
                                    }

                                }

                            }
                        }
                        if(phoneChoice.equals("5")) {
                            optionsPrompt();
                        }else{
                         System.out.println("Unknown input");
                        }
            
                     }       
                    
                    
                }
                if(back.equals("2")){
                   System.out.println("Store XXXX is nearest to you");
                   addPhone();
                }
                if(back.equals("3")) {
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                }
            
            }
        
        
        
    }
    
    public static void actuallyAddPhone(String manu, String model) throws SQLException, IOException, ClassNotFoundException{
        
        Random rand = new Random();
        int first7 = rand.nextInt((9999999 - 1000000) + 1) + 1000000;
        int last3 = rand.nextInt((999 - 100) + 1) + 100;
        String str = ""+first7+last3;
        long newNumber = Long.parseLong(str);
        
        int PID = rand.nextInt((999999 - 100000) + 1) + 100000;
        
        int iMEID = rand.nextInt((999999 - 100000) + 1) + 100000;
        String MEID = ("#" + iMEID);
        
        System.out.println(newNumber);
        
        try {
                            Connection con1=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con1=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s1=con1.createStatement();
                            Statement s2=con1.createStatement();
                            Statement s3=con1.createStatement();
                            String q1;
                            String q2;
                            String q3;
                            ResultSet result1;
                            int i1, i2, i3;
                            q1 = "INSERT INTO phone_number(NUMBERS,NID) VALUES(" + newNumber + "," + AID + ")"; //into phone_number
                            q2 = "INSERT INTO SALE(AID, PID, STOREID) VALUES(" + AID + "," + PID + "," + 123456 + ")"; //into sale
                            q3 = "INSERT INTO phone(PHONENUMBER, PID, TYPE, MANUFACTURE, MODEL, MEID) VALUES(" + newNumber + "," + PID  + "," + "'cell'"  + "," + "'" + manu + "'" + "," + "'" + model + "'" + ","+ "'"+MEID +"'" + ")"; //into phone
                            i1 = s1.executeUpdate(q1);
                            i2 = s2.executeUpdate(q2);
                            i3 = s3.executeUpdate(q3);
                            s1.close();
                            s2.close();
                            s3.close();
                            con1.close();
                        } catch(Exception e){e.printStackTrace();}
                
                 System.out.println( manu + " " + model + " with number: " + newNumber + " has been added");
                 displayPhones();

        
    }
    public static void removePhone() throws SQLException, IOException, ClassNotFoundException{
       System.out.println("please visit a store to remove a phone");
       displayPhones();
    }
    
    public static void payment() throws SQLException, IOException, ClassNotFoundException{
        totalTextLength = 0;
        totalCallLength = 0;
        totalInternetLength = 0;
        
        
        getBillingplan();
        getPhones();
        
        
        double totalPayment = 0;
        double sixteenTotal = 0;
        double fifthteenTotal = 0;
        
        double sJan = 0;
        double sFeb = 0;
        double sMar = 0;
        double sApr = 0;
        double sMay = 0;
        double sJun = 0;
        double sJul = 0;
        double sAug = 0;
        double sSep = 0;
        double sOct = 0;
        double sNov = 0;
        double sDec = 0;
        
        double fJan = 0;
        double fFeb = 0;
        double fMar = 0;
        double fApr = 0;
        double fMay = 0;
        double fJun = 0;
        double fJul = 0;
        double fAug = 0;
        double fSep = 0;
        double fOct = 0;
        double fNov = 0;
        double fDec = 0;
        
        System.out.println("Current billing plan rates: ");
        System.out.println("Plan type:  Text rate:  Phone rate:  Internet rate:  Text limit:  Phone limit:  Internet limit:");
        System.out.print(btype + "      ");
        System.out.print(trate + "         ");
        System.out.print(prate + "           ");
        System.out.print(irate + "             ");
        System.out.print(tlimit + "          ");
        System.out.print(plimit + "          ");
        System.out.print(ilimit + "      ");
        System.out.println();
        System.out.println();
            
        
        System.out.println("Please select one the following options:");
        System.out.println("[1] Payment by year");
        System.out.println("[2] Payment total");
        System.out.println("[3] Return to previous menu");
        
        
        
         long temp = currentNumber;
         
                    currentNumber = Long.parseLong(phones[0][0]);
                    for(int k = 0; k < phoneLength; k++){
                        
                        currentNumber = Long.parseLong(phones[k][0]);
                        getUsage();
                    }
                        
                       // System.out.println("text length: " + totalTextLength);
                        //System.out.println("current phone " + phones[k][0]);
                        for(int i = 0; i < totalTextLength; i++){
                          //  System.out.println("text: " + totalTexts[i][2]);
                            double toAdd = 0;
                            toAdd += (Integer.parseInt(totalTexts[i][2]) * Double.parseDouble(trate));
                           // System.out.println("to add: " + toAdd);
                            totalPayment += toAdd;
                            long year = (Long.parseLong(totalTexts[i][1]));
                            
                            //System.out.println(year);
                            for(int h = 0; h < 100; h++){
                                year= year - 2000000000;
                            }
                            
                            
                            //System.out.println(year);
                            if(year < 1600000000){
                                //System.out.println("2015");
                                fifthteenTotal += toAdd;
                                long month = (year - 1500000000);
                                
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){fFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){fMar += toAdd;}
                                if(month > 03000000 && month < 04000000){fApr += toAdd;}
                                if(month > 04000000 && month < 05000000){fMay += toAdd;}
                                if(month > 05000000 && month < 06000000){fJun += toAdd;}
                                if(month > 06000000 && month < 07000000){fJul += toAdd;}
                                if(month > 07000000 && month < 8000000){fAug += toAdd;}
                                if(month > 8000000 && month < 9000000){fSep += toAdd;}
                                if(month > 9000000 && month < 10000000){fOct += toAdd;}
                                if(month > 10000000 && month < 11000000){fNov += toAdd;}
                                if(month > 11000000 && month < 12000000){fDec += toAdd;}
                                
                                
                            
                            }else{
                                    
                                    
                                sixteenTotal +=toAdd;
                                long month = year - 1600000000;
                                //System.out.println("2016: " + month);
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){sFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){sMar += toAdd;}
                                if(month > 03000000 && month < 04000000){sApr += toAdd;}
                                if(month > 04000000 && month < 05000000){sMay += toAdd;}
                                if(month > 05000000 && month < 06000000){sJun += toAdd;}
                                if(month > 06000000 && month < 07000000){sJul += toAdd;}
                                if(month > 07000000 && month < 8000000){sAug += toAdd;}
                                if(month > 8000000 && month < 9000000){sSep += toAdd;}
                                if(month > 9000000 && month < 10000000){sOct += toAdd;}
                                if(month > 10000000 && month < 11000000){sNov += toAdd;}
                                if(month > 11000000 && month < 12000000){sDec += toAdd;}
                            }
                            
                            
                            
                        }
                        //totalTextLength = 0;
                        for(int i = 0; i < totalCallLength; i++){
                           // System.out.println("call: " + totalCalls[i][2]);
                           int toAdd = 0;
                            toAdd += (((Long.parseLong(totalCalls[i][1]))-(Long.parseLong(totalCalls[i][2]))) * Double.parseDouble(prate));
                            totalPayment += toAdd;
                            long year = (Long.parseLong(totalCalls[i][2]));
                            for(int h = 0; h < 100; h++){
                                year= year - 2000000000;
                            }
                            
                            if(year < 1600000000){
                                fifthteenTotal += toAdd;
                                long month = year - 1500000000;
                                
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){fFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){fMar += toAdd;}
                                if(month > 03000000 && month < 04000000){fApr += toAdd;}
                                if(month > 04000000 && month < 05000000){fMay += toAdd;}
                                if(month > 05000000 && month < 06000000){fJun += toAdd;}
                                if(month > 06000000 && month < 07000000){fJul += toAdd;}
                                if(month > 07000000 && month < 8000000){fAug += toAdd;}
                                if(month > 8000000 && month < 9000000){fSep += toAdd;}
                                if(month > 9000000 && month < 10000000){fOct += toAdd;}
                                if(month > 10000000 && month < 11000000){fNov += toAdd;}
                                if(month > 11000000 && month < 12000000){fDec += toAdd;}
                                
                                
                            
                            }else{
                                sixteenTotal +=toAdd;
                                long month = year - 1600000000;
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){sFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){sMar += toAdd;}
                                if(month > 03000000 && month < 04000000){sApr += toAdd;}
                                if(month > 04000000 && month < 05000000){sMay += toAdd;}
                                if(month > 05000000 && month < 06000000){sJun += toAdd;}
                                if(month > 06000000 && month < 07000000){sJul += toAdd;}
                                if(month > 07000000 && month < 8000000){sAug += toAdd;}
                                if(month > 8000000 && month < 9000000){sSep += toAdd;}
                                if(month > 9000000 && month < 10000000){sOct += toAdd;}
                                if(month > 10000000 && month < 11000000){sNov += toAdd;}
                                if(month > 11000000 && month < 12000000){sDec += toAdd;}
                            }
                        }
                        //totalCallLength = 0;
                        
                        for(int i = 0; i < totalInternetLength; i++){
                           // System.out.println("internet: " + totalInternet[i][2]);
                           int toAdd = 0;
                            toAdd += (((Long.parseLong(totalInternet[i][2]))-(Long.parseLong(totalInternet[i][2]))) * Double.parseDouble(irate));
totalPayment += toAdd;
                            long year = (Long.parseLong(totalInternet[i][0]));
                            for(int h = 0; h < 100; h++){
                                year= year - 2000000000;
                            }
                            
                            if(year < 1600000000){
                                fifthteenTotal += toAdd;
                                long month = year - 1500000000;
                                
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){fFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){fMar += toAdd;}
                                if(month > 03000000 && month < 04000000){fApr += toAdd;}
                                if(month > 04000000 && month < 05000000){fMay += toAdd;}
                                if(month > 05000000 && month < 06000000){fJun += toAdd;}
                                if(month > 06000000 && month < 07000000){fJul += toAdd;}
                                if(month > 07000000 && month < 8000000){fAug += toAdd;}
                                if(month > 8000000 && month < 9000000){fSep += toAdd;}
                                if(month > 9000000 && month < 10000000){fOct += toAdd;}
                                if(month > 10000000 && month < 11000000){fNov += toAdd;}
                                if(month > 11000000 && month < 12000000){fDec += toAdd;}
                                
                                
                            
                            }else{
                                sixteenTotal +=toAdd;
                                long month = year - 1600000000;
                                if(month < 01000000){
                                    fJan += toAdd;
                                }if(month > 01000000 && month < 02000000){sFeb += toAdd;}
                                if(month > 02000000 && month < 03000000){sMar += toAdd;}
                                if(month > 03000000 && month < 04000000){sApr += toAdd;}
                                if(month > 04000000 && month < 05000000){sMay += toAdd;}
                                if(month > 05000000 && month < 06000000){sJun += toAdd;}
                                if(month > 06000000 && month < 07000000){sJul += toAdd;}
                                if(month > 07000000 && month < 8000000){sAug += toAdd;}
                                if(month > 8000000 && month < 9000000){sSep += toAdd;}
                                if(month > 9000000 && month < 10000000){sOct += toAdd;}
                                if(month > 10000000 && month < 11000000){sNov += toAdd;}
                                if(month > 11000000 && month < 12000000){sDec += toAdd;}
                            }
                        }
                        //totalInternetLength = 0;
                    //}
                    
                    
                    currentNumber = temp;
        
        
        
        
        while (true){
         
                 
                Scanner S = new Scanner(System.in);
                String back = S.next();
                if(back.equals("1")){
                    System.out.println("Payment by year:");
                    System.out.println("2016 total: " + sixteenTotal);
                    System.out.println("    Jan total: " + sJan);
                    System.out.println("    Feb total: " + sFeb);
                    System.out.println("    Mar total: " + sMar);
                    System.out.println("    Apr total: " + sApr);
                    System.out.println("    May total: " + sMay);
                    System.out.println("    Jun total: " + sJun);
                    System.out.println("    Jul total: " + sJul);
                    System.out.println("    Aug total: " + sAug);
                    System.out.println("    Sep total: " + sSep);
                    System.out.println("    Oct total: " + sOct);
                    System.out.println("    Nov total: " + sNov);
                    System.out.println("    Dec total: " + sDec);
                     System.out.println("2015 total: " + fifthteenTotal);
                    System.out.println("    Jan total: " + fJan);
                    System.out.println("    Feb total: " + fFeb);
                    System.out.println("    Mar total: " + fMar);
                    System.out.println("    Apr total: " + fApr);
                    System.out.println("    May total: " + fMay);
                    System.out.println("    Jun total: " + fJun);
                    System.out.println("    Jul total: " + fJul);
                    System.out.println("    Aug total: " + fAug);
                    System.out.println("    Sep total: " + fSep);
                    System.out.println("    Oct total: " + fOct);
                    System.out.println("    Nov total: " + fNov);
                    System.out.println("    Dec total: " + fDec);
                    optionsPrompt();
                }
                if(back.equals("2")) {
     
                    System.out.println("Total amount owed: " + totalPayment);
                    System.out.println();
                    optionsPrompt();
                    
                }
                if(back.equals("3")){
                    optionsPrompt();
                }else{
                 System.out.println("Unknown input");
                 
                 
                }
            
            }
       
        
   
        
    }
    
    public static void addAccount()throws SQLException, IOException, ClassNotFoundException{
        
        Random rand = new Random();
        int first7 = rand.nextInt((9999999 - 1000000) + 1) + 1000000;
        int last3 = rand.nextInt((999 - 100) + 1) + 100;
        int newAID = rand.nextInt((999999 - 100000) + 1) + 100000;
        //int newPID = rand.nextInt((999999 - 100000) + 1) + 100000;
        String str = ""+first7+last3;
        long newNumber = Long.parseLong(str);

        
        System.out.println(newNumber);
        
        try {
                            Connection con1=null;
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                            con1=DriverManager.getConnection(
                              "jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
                             oraclePassword);
                            Statement s1=con1.createStatement();
                            Statement s2=con1.createStatement();
                          //  Statement s3=con1.createStatement();
                            String q1;
                            String q2;
                           // String q3;
                            ResultSet result1;
                            int i1, i2;
                            q1 = "INSERT INTO Account (AID,PNUMBER,ATYPE) VALUES (" + newAID + "," + newNumber + "," + "'individual'" +")";
                            
                            q2 = "INSERT INTO OWNS (CID, AID) VALUES (" + CID +","+ newAID+")"; //into sale
                           // q3 = "INSERT INTO phone(PHONENUMBER, PID, TYPE, MANUFACTURE, MODEL, MEID) VALUES(" + newNumber + "," + PID  + "," + "'cell'"  + "," + "'" + manu + "'" + "," + "'" + model + "'" + ","+ "'"+MEID +"'" + ")"; //into phone
                            i1 = s1.executeUpdate(q1);
                            i2 = s2.executeUpdate(q2);
                            //i3 = s3.executeUpdate(q3);
                            s1.close();
                            s2.close();
                            //s3.close();
                            con1.close();
                        } catch(Exception e){e.printStackTrace();}
                
                 System.out.println("New account with primary number: " + newNumber +" has been added");
                 optionsPrompt();
    }
    
}

