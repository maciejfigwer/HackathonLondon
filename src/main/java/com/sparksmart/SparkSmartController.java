package com.sparksmart;

import com.ciscospark.Message;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Piotr on 12/3/2016.
 */

@RestController
@RequestMapping("/")
public class SparkSmartController {

    /*@RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        SparkSmart.getSpark();
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage("this is a simple message that should be translated", Language.ENGLISH, Language.FRENCH);
        return translatedMessage;
    }*/

    public static ConcurrentHashMap<String, String> conversations = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Language> fromLang = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Language> toLang = new ConcurrentHashMap<>();

    @RequestMapping(value = "/conversation", method = RequestMethod.POST)
    public String translation(@RequestBody WebhookRequestBody body) {
        if (body.getWebhookData().getPersonEmail().endsWith("sparkbot.io")) {
            return "well done me";
        }

        String fromId = body.getActorId();

        if ("group".equals(body.getWebhookData().getRoomType()) || !conversations.contains(fromId))
            return "";

//        ArrayList<String> messages = getMessages(body);
        JSONObject message = getMessage(body.getWebhookData().getId());
        String lastMessageText = message.getString("text");
        System.out.println("lastMessage=" + lastMessageText);

//        String[] array = lastMessageText.split(" ");
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i < array.length; i++) {
//            sb.append(array[i]).append(" ");
//        }
//
//        lastMessageText = sb.toString();
        Language lang1 = fromLang.get(fromId);
        Language lang2 = toLang.get(fromId);
        String toPersonId = conversations.get(fromId);
        translateAndSendToUser(lastMessageText, toPersonId, lang1, lang2);
        return "";
    }

    @RequestMapping(value = "/sparksmart", method = RequestMethod.POST)
    public String conversation(@RequestBody WebhookRequestBody body) {
        if (body.getWebhookData().getPersonEmail().endsWith("sparkbot.io")) {
            return "well done me";
        }


//        ArrayList<String> messages = getMessages(body);
        JSONObject message = getMessage(body.getWebhookData().getId());
        String lastMessageText = message.getString("text");
        System.out.println("lastMessage=" + lastMessageText);

        String[] array = lastMessageText.split(" ");

        if (array.length >= 4 && array[1].equals("start")) {
            String id = SparkSmart.getCurrentId();
            List<String> mentionedPeople = body.getWebhookData().getMentionedPeople().stream().filter(x -> !x.equals(id)).collect(Collectors.toList());
            System.out.println(mentionedPeople.toString());

            if (mentionedPeople.isEmpty()) {
                System.out.println("No mentioned users!!!!!!!!!!!!!!");
                return "";
            }

            String englishUserId = body.getActorId();
            String frenchUserId = mentionedPeople.get(0);

            createChats(englishUserId, frenchUserId);
            return "nothing";
        }

        if (array.length == 2 && array[1].equals("stop")) {

            String toId = body.getActorId();
            if( conversations.contains(toId)) {
                String otherId = conversations.get(toId);
                conversations.remove(toId);
                conversations.remove(otherId);
            }
            return "nothing";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            sb.append(array[i]).append(" ");
        }

        lastMessageText = sb.toString();

        translateAndSend(lastMessageText, body.getWebhookData().getPersonEmail(), body.getWebhookData().getRoomId(), Language.FRENCH, Language.ENGLISH);
        return "";
    }

    private static JSONObject getMessage(String messageId) {
        try {
            Content content = Request.Get("https://api.ciscospark.com/v1/messages/" + messageId)
                    // Add headers
                    .addHeader("Authorization", "Bearer " + SparkSmart.accessToken)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    // Fetch request and return content
                    .execute().returnContent();
            // Print content
//            System.out.println(content);
            return new JSONObject(content.toString());
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static JSONObject getPerson(String personId) {
        try {
            Content content = Request.Get("https://api.ciscospark.com/v1/people/" + personId)
                    // Add headers
                    .addHeader("Authorization", "Bearer " + SparkSmart.accessToken)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    // Fetch request and return content
                    .execute().returnContent();
            // Print content
//            System.out.println(content);
            return new JSONObject(content.toString());
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private void translateAndSend(String message, String senderEmail, String roomId, Language lang1, Language lang2) {
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage(message, lang1, lang2);
        JSONObject obj = new JSONObject(translatedMessage);
        JSONObject translation = (JSONObject) obj.getJSONArray("translations").get(0);
        postMessageOnRoom(roomId, translation.getString("translation"), senderEmail);
    }

    private void translateAndSendToUser(String message, String personId, Language lang1, Language lang2) {
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage(message, lang1, lang2);
        JSONObject obj = new JSONObject(translatedMessage);
        JSONObject translation = (JSONObject) obj.getJSONArray("translations").get(0);
        postMessageToUser(personId, translation.getString("translation"));
    }

    private void createChats(String englishUserId, String frenchUserId) {

        JSONObject englishUser = getPerson(englishUserId);
        JSONObject frenchUser = getPerson(frenchUserId);
        String englishUserName = englishUser.getString("displayName");
        String frenchUserName = frenchUser.getString("displayName");
        System.out.println("english user=" + englishUserName);
        System.out.println("french user=" + frenchUserName);
        //fName = SparkSmart.getSpark().people().get(new )
        String message = "You are chatting with " + englishUserName;
        postMessageToUser(frenchUserId, message);

        message = "You are chatting with " + frenchUserName;
        postMessageToUser(englishUserId, message);

        conversations.put(englishUserId, frenchUserId);
        conversations.put(frenchUserId, englishUserId);

        fromLang.put(englishUserId, Language.ENGLISH);
        fromLang.put(frenchUserId, Language.FRENCH);

        toLang.put(englishUserId, Language.FRENCH);
        toLang.put(frenchUserId, Language.ENGLISH);
    }

    private void postMessageOnRoom(String roomId, String messageText, String senderEmail) {
        Message message = new Message();
        message.setRoomId(roomId);
        message.setText(senderEmail + " said: " + messageText);
        SparkSmart.getSpark().messages().post(message);
    }

    private void postMessageToUser(String userId, String messageText) {
        Message message = new Message();
        message.setToPersonId(userId);
        message.setText(messageText);
        SparkSmart.getSpark().messages().post(message);
    }

    private ArrayList<String> getMessages(WebhookRequestBody body) {
        System.out.println(getMessage(body.getWebhookData().getId()));

        final ArrayList<String> messages = new ArrayList<>();
        final HashMap<String, Integer> count = new HashMap<>();
        count.put("count", 0);
        SparkSmart.getSpark().messages().queryParam("roomId", body.getWebhookData().getRoomId()).queryParam("mentionedPeople", "me").queryParam("max", "1").iterate().forEachRemaining(message -> {
            int countInt = count.get("count");
            System.out.println(countInt + " message=" + message.getId() + "=" + message.getText());
            messages.add(message.getText());
            count.put("count", countInt + 1);
        });
        return messages;
    }

}
