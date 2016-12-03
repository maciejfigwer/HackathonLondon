package com.sparksmart;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;

/**
 * Created by Piotr on 12/3/2016.
 */
public class WatsonTranslationService {
    public static LanguageTranslator translationService;

    public static void initializeOrIgnoreTranslationService() {
        if (translationService == null) {
            translationService  = new LanguageTranslator();
            translationService.setUsernameAndPassword("bc31faa4-3eb1-41ea-9bf3-71930557b3bd","Pry84xL3rivn");
        }
    }

    public static String watsonTranslateMessage(String messageToTranslate, Language fromLanguage, Language toLanguage) {
        initializeOrIgnoreTranslationService();

        // String message = "Ca va? Je suis charlie et j'habit a Londre";
        // TranslationResult translationResult = translationService.translate(messageToTranslate, Language.FRENCH, Language.ENGLISH).execute();
        TranslationResult translationResult = translationService.translate(messageToTranslate, fromLanguage, toLanguage).execute();

        System.out.println(translationResult);
        return translationResult.toString();
    }

}
