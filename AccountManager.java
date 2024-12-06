import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;
          
    public AccountManager(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
      public void credit_money(long account_number)throws SQLException{
        scanner.nextLine();
        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        System.out.println("Enter Password: ");
        String security_pin = scanner.nextLine();
       try {
        connection.setAutoCommit(false);
        if (account_number!=0) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number =? AND security_pin=?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number=?";
                PreparedStatement preparedStatement2= connection.prepareStatement(credit_query);
                preparedStatement2.setDouble(1, amount);
                preparedStatement2.setLong(2, account_number);
                int rowsAffected = preparedStatement2.executeUpdate();
                if (rowsAffected>0) {
                    System.out.println("Rs"+amount+"credited successfully");
                    connection.commit();
                    connection.setAutoCommit(true);
                    
                } else {
                    System.out.println("Transcation failed");
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } else {
                System.out.println("account doesn't exits");
            }
        } else {
            System.out.println("Invaild account number");
        }
       } catch (SQLException e) {
              e.printStackTrace();
       }
       connection.setAutoCommit(true);
      }

    public void debit_money(long account_number)throws SQLException{
        scanner.nextLine();
        System.out.println("Enter Amount: ");
        double amount = scanner.nextDouble();
        System.out.println("Enter Password: ");
        String security_pin = scanner.nextLine();
        scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number!=0) {
        
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number =? AND security_pin=?");
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double current_balance = resultSet.getDouble("balance");
                    if (amount<=current_balance) {
                        String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number=?";
                        PreparedStatement preparedStatement1= connection.prepareStatement(debit_query);
                        preparedStatement1.setDouble(1, amount);
                        preparedStatement1.setLong(2, account_number);
                        int rowsAffected = preparedStatement1.executeUpdate();
                        if (rowsAffected>0) {
                            System.out.println("Rs "+amount+ " debited successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                        
                    } else {
                        System.out.println("Insufficient balance");
                    }
                    
                } else {
                    System.out.println("Account doesn't exits");
                }
                
            } else {
                System.out.println("invalid account number");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
          connection.setAutoCommit(true);
    }

    public void transfer_money(long sender_account_number)throws SQLException{
        scanner.nextLine();
        System.out.println("Enter receiver's account number : ");
        long receiver_account_number = scanner.nextLong();
        System.out.println("Enter amount : ");
        double amount = scanner.nextDouble();
        System.out.println("Enter password : ");
        String security_pin = scanner.nextLine();
        try {
            connection.setAutoCommit(false);
            if (receiver_account_number!=0 && sender_account_number!=0) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM accounts WHERE account_number =? AND security_pin=?");
                preparedStatement.setLong(1, sender_account_number);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double current_balance = resultSet.getDouble("balance");
                if (amount<=current_balance) {
                String debit_query = "UPDATE accounts SET balance = balance-? WHERE account_number=?";
                String credit_query= "UPDATE accounts SET balance = balance + ? WHERE account_number=?";
                
                PreparedStatement preparedStatement_credit = connection.prepareStatement(credit_query);
                PreparedStatement preparedStatement_debit = connection.prepareStatement(debit_query);
                //credit
                preparedStatement_credit.setDouble(1, amount);
                preparedStatement_credit.setLong(2, receiver_account_number);
                //debit
                preparedStatement_debit.setDouble(1,amount );
                preparedStatement_debit.setLong(2,sender_account_number);

                int rowsAffected1 = preparedStatement_credit.executeUpdate();
                int rowsAffected2 = preparedStatement_debit.executeUpdate();
                if (rowsAffected1>0 && rowsAffected2 >0) {
                    System.out.println("Transaction successfull");
                    System.out.println("Rs"+amount+"transfered successfully");
                    connection.commit();
                    connection.setAutoCommit(true);
                    
                    
                } else {
                    System.out.println("Transcation failed");
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

            } else {
                System.out.println("Insufficient balance");
            }
 
            } else {
                System.out.println("account doesn't exits");
            }
            } else {
                System.out.println("Invalid account number or Security pin ");
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void getBalance(long account_number){
        scanner.nextLine();
        System.out.println("Enter Security pin :");
        String security_pin = scanner.nextLine();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM accounts WHERE account_number = ? AND security_pin =?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2,security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                 double balance = resultSet.getDouble("balance");
                 System.out.println("balance :"+balance);
            } else {
                System.out.println("Invalid pin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
}
