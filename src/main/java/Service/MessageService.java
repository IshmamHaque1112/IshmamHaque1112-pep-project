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
    public List<Message> getMessagesbyAuthorID(int authorid){
        return messageDAO.getAllAccountMessages(authorid);
    }
    public Message insertMessage(int authorid,String message_text,Long timepost){
        return messageDAO.insertMessage(authorid,message_text,timepost);
    }
    public Message insertMessage(Message message){
        return messageDAO.insertMessage(message);
    }
    public Message updateMessage(int authorid,int messageid,String newmessage_text) throws IllegalArgumentException{
      try{
        if(messageDAO.getMessagebyId(messageid).getPosted_by()!=authorid) throw new IllegalArgumentException("Message author is not as same as current author");
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

}