
//package database;

import java.io.*;
import java.sql.*;
import java.util.Scanner;


public class database {

    public static String oracleID;
    public static String oraclePassword;
    
    public static void main(String[] args)
	throws SQLException, IOException, java.lang.ClassNotFoundException {
	Class.forName ("oracle.jdbc.driver.OracleDriver");
        
        interfaces interfaces = new interfaces();
        
        
        if(loginRoutine() == 1){
            interfaces.run(oracleID,oraclePassword);
            
        }
        
    }
    
    
    public static int loginRoutine(){
	System.out.println("enter Oracle user id: ");
        Scanner IDScanner = new Scanner(System.in);
        oracleID = IDScanner.next();
	
	System.out.println("enter Oracle password for" + " " + oracleID + ":");
        Scanner passwordScanner = new Scanner(System.in);
        oraclePassword = passwordScanner.next();
	
	boolean loginCheck;
        
       	try{ //check login information
	    Connection con = DriverManager.getConnection
		("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",oracleID,
		 oraclePassword);
	    con.close();
	    loginCheck = true;
            //System.out.println("welcome");
            return 1;
        }
	catch(SQLException e){
            System.out.println("invalid username or password");
            loginCheck = false;
            loginRoutine();
            return 0;
        }
        
    }

    
}
