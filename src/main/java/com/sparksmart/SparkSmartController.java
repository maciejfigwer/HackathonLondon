package com.sparksmart;

import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Piotr on 12/3/2016.
 */

@RestController
@RequestMapping("/sparksmart")
public class SparkSmartController {

    @RequestMapping(method = RequestMethod.GET)
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        SparkSmart.getSpark();
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage("this is a simple message that should be translated", Language.ENGLISH, Language.FRENCH);
        return translatedMessage;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String translation(@RequestBody WebhookRequestBody body) {
        System.out.println(body.getWebhookData());
        System.out.println(body.getWebhookData().getPersonEmail());
        SparkSmart.getSpark().messages().get();
        SparkSmart.getSpark().messages().iterate().forEachRemaining(message -> {
            System.out.println("message=" + message.toString());
        });
        String translatedMessage = WatsonTranslationService.watsonTranslateMessage("this is a simple message that should be translated", Language.ENGLISH, Language.FRENCH);
        return translatedMessage;
    }

}
