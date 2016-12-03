package com.sparksmart;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Piotr on 12/3/2016.
 */
/*
        "id":"Y2lzY29zcGFyazovL3VzL1dFQkhPT0svNWQzZDBjMDgtMDA3Ni00NTNhLTkwM2QtN2FmNjBmNGQ2MmU0",
        "name":"SparkSmart translation",
        "targetUrl":"http://964035d5.ngrok.io/sparksmart",
        "resource":"messages",
        "event":"created",
        "filter":"roomId=Y2lzY29zcGFyazovL3VzL1JPT00vZTdiMjM1NzAtYjk1YS0xMWU2LWEwNTAtMjMwY2RlZmJiYTBk",
        "orgId":"Y2lzY29zcGFyazovL3VzL09SR0FOSVpBVElPTi9jb25zdW1lcg",
        "createdBy":"Y2lzY29zcGFyazovL3VzL1BFT1BMRS9jZWUxOGZiMi1iMTVkLTRmYzktYjQxNS1mYmQ1YWJjOWE2MGI",
        "appId":"Y2lzY29zcGFyazovL3VzL0FQUExJQ0FUSU9OL0MzMmM4MDc3NDBjNmU3ZGYxMWRhZjE2ZjIyOGRmNjI4YmJjYTQ5YmE1MmZlY2JiMmM3ZDUxNWNiNGEwY2M5MWFh",
        "ownedBy":"creator",
        "status":"active",
        "created":"2016-12-03T18:25:40.653Z",
        "actorId":"Y2lzY29zcGFyazovL3VzL1BFT1BMRS9mNmM4N2QxYi1jZjk0LTRhMGUtODY5Yi1kMzdiODkwNzRmMjk",
        "webhookData":{
        "id":"Y2lzY29zcGFyazovL3VzL01FU1NBR0UvNjcwOTA3NjAtYjk4Ni0xMWU2LWE5MGYtMWQyMjZkOWYzZTY1",
        "roomId":"Y2lzY29zcGFyazovL3VzL1JPT00vZTdiMjM1NzAtYjk1YS0xMWU2LWEwNTAtMjMwY2RlZmJiYTBk",
        "roomType":"group",
        "personId":"Y2lzY29zcGFyazovL3VzL1BFT1BMRS9mNmM4N2QxYi1jZjk0LTRhMGUtODY5Yi1kMzdiODkwNzRmMjk",
        "personEmail":"piotr.nadczuk@gmail.com",
        "mentionedPeople":[
        "Y2lzY29zcGFyazovL3VzL1BFT1BMRS9jZWUxOGZiMi1iMTVkLTRmYzktYjQxNS1mYmQ1YWJjOWE2MGI"
        ],
        "created":"2016-12-03T18:29:18.678Z"
        }*/
public class WebhookRequestBody {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String targetUrl;
    @JsonProperty
    private String resource;
    @JsonProperty
    private String event;
    @JsonProperty
    private String filter;
    @JsonProperty
    private String orgId;
    @JsonProperty
    private String createdBy;
    @JsonProperty
    private String appId;
    @JsonProperty
    private String ownedBy;
    @JsonProperty
    private String status;
    @JsonProperty
    private String created;
    @JsonProperty
    private String actorId;
    @JsonProperty
    private WebhookData data;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getResource() {
        return resource;
    }

    public String getEvent() {
        return event;
    }

    public String getFilter() {
        return filter;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAppId() {
        return appId;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }

    public String getActorId() {
        return actorId;
    }

    public WebhookData getWebhookData() {
        return data;
    }
}
