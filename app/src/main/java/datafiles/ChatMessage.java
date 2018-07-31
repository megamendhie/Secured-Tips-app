package datafiles;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ChatMessage {
    private String messageUser;
    private String messageText;
    private String messageUserId;
    private String messageStatus;
    private long messageTime;
    private long  messageLikesCount;
    public Map<String, Boolean> messageLikes = new HashMap<>();

    public ChatMessage(String messageUser, String messageText, String messageUserId, String messageStatus, long messageLikesCount, Map<String, Boolean> messageLikes) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        messageTime = new Date().getTime();
        this.messageUserId = messageUserId;
        this.messageStatus = messageStatus;
        this.messageLikesCount = messageLikesCount;
        this.messageLikes= messageLikes;
    }

    public ChatMessage(){

    }

    public String getMessageUserId() {
        return messageUserId;
    }

    public void setMessageUserId(String messageUserId) {
        this.messageUserId = messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public Map<String, Boolean> getMessageLikes() {
        return messageLikes;
    }

    public void setMessageLikes(Map<String, Boolean> messageLikes) {
        this.messageLikes = messageLikes;
    }

    public long getMessageLikesCount() {
        return messageLikesCount;
    }

    public void setMessageLikesCount(long messageLikesCount) {
        this.messageLikesCount = messageLikesCount;
    }
}
