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
            Class.forName("com.mysql.cj.jdbc.Drive");
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
                System.out.println();
                System.out.println("*** WELCOME TO BANK ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");
                int choice1 = scanner.nextInt();
                switch (choice1) {
                    case 1:
                       user.register();
                        break;
                    case 2:
                       email = user.login();
                       if(email!=null){
                        System.out.println();
                        System.out.println("User Logged In!");
                        if (!accounts.account_exits(email)) {
                            System.out.println();
                            System.out.println("1. Open a New Bank Account");
                            System.out.println("2. Exit");
                            if (scanner.nextInt()==1) {
                                account_number = accounts.open_account(email);
                                System.out.println("Account created successfully");
                                System.out.println("Your Account number is :"+account_number);
                            
                            } else {
                                break;
                            }
                            
                        }
                        account_number= accounts.getAcountNumber(email);
                        int choice2 =0;
                        while (choice2!=5) {
                        System.out.println();
                        System.out.println("1. Debit Money");
                        System.out.println("2. Credit Money");
                        System.out.println("3. Transfer Money");
                        System.out.println("4. Check Money");
                        System.out.println("5. Log out");
                        System.out.println("Enter your choice");
                        choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 1:
                            accountManager.debit_money(account_number);
                                break;
                            case 2:
                            accountManager.credit_money(account_number);
                                break;
                            case 3:
                            accountManager.transfer_money(account_number);
                               break;
                            case 4:
                            accountManager.getBalance(account_number);
                               break;
                            case 5:
                              break;
                            default:
                            System.out.println("Enter a valid choice!");
                                break;
                        }   
                        }

                       } else {
                        System.out.println("Name or Password is Incorrect ");
                       }
                      case 3 :
                      System.out.println("THANK YOU FOR USING OUR BANK!");
                      System.out.println("Exiting System...");
                      return;
                    default:
                    System.out.println("Enter a Valid choice ");
                        break;
                }
             }
            
          
            
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
     
    }
}