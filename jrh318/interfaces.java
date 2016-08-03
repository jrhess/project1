//package database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author jordanhess
 */
public class interfaces {
    
    public static String oracleID;
    public static String oraclePassword;
    
    public static void main(String[] args){

    }
    
    public static void run(String id, String password) throws IOException, SQLException, ClassNotFoundException{//driver of everything
        oracleID = id;
        oraclePassword = password;
                
        welcomeMessage();
        chooseUser();
       

    }
    
    public static void welcomeMessage(){
        System.out.println("Welcome to the Jog Wireless Network!");
        System.out.println();
        System.out.println();
        System.out.println("\"We're like Sprint, only slower\"");
        System.out.println();
        System.out.println();
        System.out.println("Please enter one of the following to proceed Employee or Customer");
    }
    
    public static void chooseUser() throws IOException, SQLException, ClassNotFoundException{ 
        Scanner loginS = new Scanner(System.in);
        String loginType = loginS.next();
        
        if(loginType.equals("Employee") || loginType.equals("employee")){
            employee employee = new employee();
            employee.runInterface(oracleID, oraclePassword);
            
        }
        if(loginType.equals("Customer") || loginType.equals("customer")){
            customer customer = new customer();
            customer.runInterface(oracleID, oraclePassword);
            

        }else{
            System.out.println("Please enter either Employee or Customer");
                chooseUser();
                
        }
        
        
        
    }
        
    
}
