import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection , Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public long open_account(String email){
        if(!account_exits(email)){
            String query = "INSERT INTO accounts(account_number,full_name,email,balance,security_pin) VALUES(?,?,?,?,?)";
            scanner.nextLine();
            System.out.println("Enter you fullName :");
            String full_name=scanner.nextLine();
            System.out.println("Enter Intial amount :");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter security pin :");
            String security_pin = scanner.nextLine();
            try {
                long account_number = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5,security_pin);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected>0) {
                   return account_number;
                } else {
                    throw new RuntimeException("Account creation unsuccessfull");
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
 
    }
    throw new RuntimeException(" ACCOUNT ALREADY EXITS");
}

    public long getAcountNumber(String email){
        String query = "SELECT account_number FROM accounts WHERE email=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("account_number");
            }
        }
         catch (SQLException e) {
            e.printStackTrace();
        }
    
    throw new RuntimeException( "ACCOUNT NUMBER DOESN'T EXITS");
}
    public long generateAccountNumber(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT account_number FROM accounts ORDER BY account_number DESC LIMIT 1");
            if (resultSet.next()) {
                long lastAcountNumber = resultSet.getLong("account_number");
                return lastAcountNumber+1;
            } else {
                  return 10000100;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000100;
    }

    public boolean account_exits(String email){
        String query = "SELECT * FROM accounts WHERE email =?";
           try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
           } catch (SQLException e)  {
           e.printStackTrace();
           }
           return false;
    }
}

