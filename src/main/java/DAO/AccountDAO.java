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
    public boolean login_valid(String username,String password){
        Account returnedaccount=getAccountbyUsername(username);
        if(returnedaccount==null) return false;
        if(returnedaccount.getPassword()==password) return true;
        else return false;
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
            if(login_valid(username, password)==false) return null;       
            preparedStatement.executeUpdate();
            return getAccountbyUsername(username);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean deleteAccount(String username,String password){
        Connection connection=ConnectionUtil.getConnection();
        try{
            String sql = "Delete from account where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            if(login_valid(username, password)==false) return false;
            preparedStatement.executeUpdate();
            return true;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
