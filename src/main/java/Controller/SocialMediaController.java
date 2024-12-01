package Controller;
import java.util.*;
import java.sql.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final AccountService accountService;
    private final MessageService messageService;
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
     public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/accounts", this::getAllAccountsHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagebyIDHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesbyAccountIDHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessagebyIDHandler);
        app.delete("/accounts", this::deleteAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/register", this::registerAccountHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);


        app.start(8080);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void getAllAccountsHandler(Context ctx) {
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts);
        ctx.status(200);
    }
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
        ctx.status(200);
    }
    private void getMessagesbyAccountIDHandler(Context ctx){
        ObjectMapper mapper = new ObjectMapper();
        int account_id=Integer.parseInt(Objects.requireNonNull(ctx.pathParam("account_id")));
        List<Message> messages= messageService.getMessagesbyAccountID(account_id);
        ctx.json(messages);
        ctx.status(200);
    }
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.insertMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }
    private void getMessagebyIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message return_message = messageService.getMessagebyId(message_id);
        if(return_message!=null){
            ctx.json(mapper.writeValueAsString(return_message));
            ctx.status(200);
        }else{
            ctx.status(200);
        }
    }
    private void deleteMessagebyIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id=Integer.parseInt(Objects.requireNonNull(ctx.pathParam("message_id")));
        Message delete_message = messageService.deleteMessage(messageService.getMessagebyId(message_id).getPosted_by(),message_id);
        if(delete_message!=null){
            ctx.json(mapper.writeValueAsString(delete_message));
            ctx.status(200);
        }else{
            ctx.status(200);
        }
    }
    private void deleteAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account delete_account = accountService.deleteAccount(account);
        if(delete_account!=null){
            ctx.json(mapper.writeValueAsString(delete_account));
            ctx.status(200);
        }else{
            ctx.status(200);
        }
    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(message.getPosted_by(),message.getMessage_id(),message.getMessage_text());
        if(updatedMessage!=null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
            ctx.status(200);
        }else{
            ctx.status(400);
        }

    }
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.insertAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }
    private void loginAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.Validatelogin(account);
        if(loginAccount!=null){
            ctx.json(mapper.writeValueAsString(loginAccount));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }


}