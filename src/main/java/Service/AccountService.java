package Service;
import Model.Account;
import DAO.AccountDAO;
import java.io.*;
import java.util.*;
public class AccountService {
    private AccountDAO accountDAO;
   
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
    public Account getAccountbyID(int id){
        return accountDAO.getAccountbyId(id);
    }
    public Account getAccountbyUsername(String username){
        return accountDAO.getAccountbyUsername(username);
    }
    public Account insertAccount(String username,String password) throws IllegalArgumentException {
        try{
            if(accountDAO.getAccountbyUsername(username)!=null) throw new IllegalArgumentException("Username already exists");
            if((username.length()<4)||(password.length()<4)) throw new IllegalArgumentException("Username or password too short");
            return accountDAO.insertAccount(username,password);
        }
        catch(IllegalArgumentException e){
            return null;

        }
    }
    public Account insertAccount(Account account) throws IllegalArgumentException {
        try{
            if(accountDAO.getAccountbyUsername(account.getUsername())!=null) throw new IllegalArgumentException("Username already exists");
            if((account.getUsername().length()<4)||(account.getPassword().length()<4)) throw new IllegalArgumentException("Username or password too short");
            return accountDAO.insertAccount(account);
        }
        catch(IllegalArgumentException e){
            return null;

        }
    }
    public Account Validatelogin(String username,String password){
        return accountDAO.login_valid(username,password);
    }
    public Account Validatelogin(Account account){
        return accountDAO.login_valid(account.getUsername(),account.getPassword());
    }
    public Account updatePassword(String username,String password,String newpassword) throws IllegalArgumentException {
        try{
            if(accountDAO.login_valid(username,password)==null) throw new IllegalArgumentException("Invalid login");
            if(newpassword.length()<4) throw new IllegalArgumentException("New password too short");
            return accountDAO.updateAccountPassword(username,password,newpassword);
        }
        catch(IllegalArgumentException e){
            return null;
        }

    }
    public Account deleteAccount(String username,String password) throws IllegalArgumentException{
        try{
            if(accountDAO.login_valid(username,password)==null){
                throw new IllegalArgumentException("Invalid login");
            }
            return accountDAO.deleteAccount(username,password);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
    public Account deleteAccount(Account account){
        return accountDAO.deleteAccount(account);

    }

}