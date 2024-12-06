import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

class BankingApp {
    private static final String url ="jdbc:mysql://localhost:3306/banking_system";
    private static final String username ="root";
    private static final String password ="aman";
    public static void main(String[] args) throws ClassNotFoundException , SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
          System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;

             while(true){
                System.out.println("Welcome to bank ");
             }
            
          
            
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }



     
    }
}