package com.sparksmart;

import com.ciscospark.Message;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

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

    @RequestMapping(value = "/sparksmart", method = RequestMethod.POST)
    public String translation(@RequestBody WebhookRequestBody body) {
        if (body.getWebhookData().getPersonEmail().endsWith("sparkbot.io")) {
            return "well done me";
        }

        ArrayList<String> messages = getMessages(body);
        String lastMessage = messages.remove(0);
        System.out.println("lastMessage=" + lastMessage);
        String[] array = lastMessage.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < array.length; i++) {
            sb.append(array[i]).append(" ");
        }

        lastMessage = sb.toString();

        String translatedMessage = WatsonTranslationService.watsonTranslateMessage(lastMessage, Language.FRENCH, Language.ENGLISH);
        JSONObject obj = new JSONObject(translatedMessage);
        JSONObject translation = (JSONObject) obj.getJSONArray("translations").get(0);
        postMessageOnRoom(body.getWebhookData().getRoomId(), translation.getString("translation"), body.getWebhookData().getPersonEmail());
        return translatedMessage;
    }

    @RequestMapping( value = "/conversation", method = RequestMethod.POST)
    public String conversation(@RequestBody WebhookRequestBody body) {
        if (body.getWebhookData().getPersonEmail().endsWith("sparkbot.io")) {
            return "well done me";
        }

        ArrayList<String> messages = getMessages(body);
        String lastMessage = messages.remove(0);
        System.out.println("lastMessage=" + lastMessage);
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage(lastMessage, Language.FRENCH, Language.ENGLISH);
        JSONObject obj = new JSONObject(translatedMessage);
        JSONObject translation = (JSONObject) obj.getJSONArray("translations").get(0);
        postMessageOnRoom(body.getWebhookData().getRoomId(), translation.getString("translation"), body.getWebhookData().getPersonEmail());
        return translatedMessage;
    }

    private void postMessageOnRoom(String roomId, String messageText, String senderEmail) {
        Message message = new Message();
        message.setRoomId(roomId);
        message.setText(senderEmail + " said: " + messageText);
        SparkSmart.getSpark().messages().post(message);
    }

    private ArrayList<String> getMessages(WebhookRequestBody body) {
        final ArrayList<String> messages = new ArrayList<>();
        final HashMap<String, Integer> count = new HashMap<>();
        count.put("count", 0);
        SparkSmart.getSpark().messages().queryParam("roomId", body.getWebhookData().getRoomId()).queryParam("mentionedPeople", "me").iterate().forEachRemaining(message -> {
            int countInt = count.get("count");
            System.out.println(countInt + " message=" + message.getId() + "=" + message.getText());
            messages.add(message.getText());
            count.put("count", countInt + 1);
        });
        return messages;
    }

}
