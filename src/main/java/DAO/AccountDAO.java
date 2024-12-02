package DAO;
import java.util.*;
import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "Select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public Account getAccountbyId(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "Select * from account where account_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account getAccountbyUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "Select * from account where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account login_valid(String username,String password){
        //System.out.println(username+" "+password);
        Account returnedaccount=getAccountbyUsername(username);
        if(returnedaccount==null){
            System.out.println("Null found");
            return null;
        }
        //System.out.println(returnedaccount.getUsername()+" "+returnedaccount.getPassword());
        if(returnedaccount.getPassword().equals(password)){ 
            System.out.println("logged in");
            return returnedaccount;
        }
        return null;
    }
    public Account login_valid(Account account){
        //System.out.println(account.getUsername()+" "+account.getPassword());
        Account returnedaccount=getAccountbyUsername(account.getUsername());
        if(returnedaccount==null){
            System.out.println("Null found"); 
            return null;
        }
        //System.out.println(returnedaccount.getUsername()+" "+returnedaccount.getPassword());
        if(returnedaccount.getPassword().equals(account.getPassword())){
            System.out.println("logged in");  
            return returnedaccount;
        }
        return null;
    }
    public Account insertAccount(String username,String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username,password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,username,password);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username,password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id,account.getUsername(),account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account updateAccountPassword(String username,String password,String newpassword){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Update Account set password=? where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newpassword);
            preparedStatement.setString(2, username);     
            if(login_valid(username, password)==null) return null;       
            preparedStatement.executeUpdate();
            return getAccountbyUsername(username);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account deleteAccount(String username,String password){
        Connection connection=ConnectionUtil.getConnection();
        try{
            String sql = "Delete from account where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            if(login_valid(username, password)==null) return null;
            Account deletedaccount=getAccountbyUsername(username);
            preparedStatement.executeUpdate();
            return deletedaccount;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account deleteAccount(Account account){
        Connection connection=ConnectionUtil.getConnection();
        try{
            String sql = "Delete from account where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            if(login_valid(account.getUsername(),account.getPassword())==null) return null;
            Account deletedaccount=getAccountbyUsername(account.getUsername());
            preparedStatement.executeUpdate();
            return deletedaccount;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
