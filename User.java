import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Scanner scanner;
    private Connection connection;


    public User(Connection connection , Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void register(){
        scanner.nextLine();
        System.out.println("Full Name : ");
       String full_name = scanner.nextLine();
        System.out.println("Enter your Email id : ");
       String email = scanner.nextLine();
        System.out.println("Password : ");
       String password = scanner.nextLine();
        if(user_exits(email)){
        System.out.println("User already exits for this email");
         return;
        }
       String register_query="INSERT INTO user(full_name,email,password) VALUES(?,?,?)";
       try {
        PreparedStatement statement = connection.prepareStatement(register_query);
        statement.setString(1, full_name);
        statement.setString(2, email);
        statement.setString(3, password);
        int affectedRows = statement.executeUpdate();
        if(affectedRows>0){
            System.out.println("Registration successful!");
        }else {
            System.out.println("Registration failed");
        }

       } catch (SQLException e) {
        e.printStackTrace();
        
       }
    }
     public String login(){
        scanner.nextLine();
        System.out.println("Enter email:");
      String email = scanner.nextLine();
      System.out.println("Password :");
      String password = scanner.nextLine();

      String login_query = "SELECT * FROM user WHERE email = ? AND password = ?";
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(login_query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
             return email;
        } else {
            return null;
        }
        
      } catch (SQLException e) {
        e.printStackTrace();
      }
    return null;

       }

    public boolean user_exits(String email){
        String userExitsQuery = "SELECT * FROM user WHERE email=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(userExitsQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
            
        } catch (SQLException e) {
          e.printStackTrace(); 
        }
        return false;


    }

}
