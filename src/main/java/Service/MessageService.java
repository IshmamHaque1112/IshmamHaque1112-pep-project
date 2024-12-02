package Service;
import Model.Message;
import DAO.MessageDAO;
import java.io.*;
import java.util.*;
public class MessageService {
    private MessageDAO messageDAO;
   
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public List<Message> getAllMessages() {
        return messageDAO.getallMessages();
    }
    public Message getMessagebyId(int id){
        return messageDAO.getMessagebyId(id);
    }
    public List<Message> getMessagesbyAccountID(int authorid){
        return messageDAO.getAllAccountMessages(authorid);
    }
    public Message insertMessage(int authorid,String message_text,Long timepost) throws IllegalAccessException{
        try{
        if(message_text.length()==0) throw new IllegalArgumentException("Blank message");
        if(message_text.length()>255) throw new IllegalArgumentException("Message longer than 255 characters");
        return messageDAO.insertMessage(authorid,message_text,timepost);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
    public Message insertMessage(Message message) throws IllegalArgumentException{
        try{
            if(message.getMessage_text().length()==0) throw new IllegalArgumentException("Blank message");
            if(message.getMessage_text().length()>255) throw new IllegalArgumentException("Message longer than 255 characters");
            return messageDAO.insertMessage(message);
            }
            catch(IllegalArgumentException e){
                return null;
            }
    }
    public Message updateMessage(int authorid,int messageid,String newmessage_text) throws IllegalArgumentException{
      try{
        if(newmessage_text.length()==0) throw new IllegalArgumentException("Blank message");
        if(newmessage_text.length()>255) throw new IllegalArgumentException("Message longer than 255 characters");
        if(messageDAO.getMessagebyId(messageid)==null) throw new IllegalArgumentException("Message does not exist");
        if(messageDAO.getMessagebyId(messageid).getPosted_by()!=authorid) throw new IllegalArgumentException("Message author is not as same as current author");
        return messageDAO.updateMessage(messageid,newmessage_text);
      }
      catch(IllegalArgumentException e){
        return null;
      }
    }
    public Message updateMessage(int messageid,String newmessage_text) throws IllegalArgumentException{
        try{
          if(newmessage_text.length()==0) throw new IllegalArgumentException("Blank message");
          if(newmessage_text.length()>255) throw new IllegalArgumentException("Message longer than 255 characters");
          if(messageDAO.getMessagebyId(messageid)==null) throw new IllegalArgumentException("Message does not exist");
          return messageDAO.updateMessage(messageid,newmessage_text);
        }
        catch(IllegalArgumentException e){
          return null;
        }
    }
    public Message deleteMessage(int authorid,int message_id) throws IllegalArgumentException{
        try{
            if(messageDAO.getMessagebyId(message_id).getPosted_by()!=authorid){
                throw new IllegalArgumentException("Invalid login");
            }
            return messageDAO.deleteMessage(message_id);
        }
        catch(IllegalArgumentException e){
            return null;
        }
    }
    public Message deleteMessage(Message message){
        return messageDAO.deleteMessage(message.getMessage_id());
    }
    public Message deleteMessage(int message_id){
        return messageDAO.deleteMessage(message_id);
    }

}