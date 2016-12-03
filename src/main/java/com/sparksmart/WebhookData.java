package com.sparksmart;

import java.util.List;

/**
 * Created by Piotr on 12/3/2016.
 */
public class WebhookData {

    private String id;
    private String roomId;
    private String roomType;
    private String personId;
    private String personEmail;
    private List<String> mentionedPeople;
    private String created;

    public String getId() {
        return id;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getPersonId() {
        return personId;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public List<String> getMentionedPeople() {
        return mentionedPeople;
    }

    public String getCreated() {
        return created;
    }

}
