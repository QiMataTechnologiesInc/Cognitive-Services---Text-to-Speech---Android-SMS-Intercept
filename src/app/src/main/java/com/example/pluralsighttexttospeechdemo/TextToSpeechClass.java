package com.example.pluralsighttexttospeechdemo;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TextToSpeechClass {
    private String _apiKey;
    private BearerToken _bearerToken;

    public TextToSpeechClass(String apiKey) {
        _apiKey = apiKey;
    }

    public void RefreshBearerToken() throws UnirestException {
        String token = Unirest.post("https://eastus.api.cognitive.microsoft.com/sts/v1.0/issueToken")
                .header("Ocp-Apim-Subscription-Key",_apiKey)
                .asString().getBody();

        _bearerToken = new BearerToken(token);
    }

    public InputStream ConvertTextToAudio(String text) throws UnirestException {
        if (_bearerToken == null || !_bearerToken.IsValid()) {
            RefreshBearerToken();
        }

        String requestSSML = GetSSML(text);

        Map<String,Object> audioHeaders = new HashMap<String, Object>();
        audioHeaders.put("Content-Type","application/ssml+xml");
        audioHeaders.put("X-Microsoft-OutputFormat","audio-24khz-48kbitrate-mono-mp3");
        audioHeaders.put("User-Agent","PluralsightSMSToAudio");
        audioHeaders.put("Authorization",_bearerToken.HeaderValue());

        InputStream audio = Unirest.post("https://eastus.tts.speech.microsoft.com/cognitiveservices/v1")
                .headers(audioHeaders)
                .body(requestSSML)
                .asBinary().getBody();

        return audio;
    }

    private String GetSSML(String text) {
        String ssml = "<speak version='1.0' xmlns=\"http://www.w3.org/2001/10/synthesis\" xml:lang='en-US'>" +
                "<voice  name='Microsoft Server Speech Text to Speech Voice (en-US, Jessa24kRUS)'>" +
                "{{ ReplaceText }}" +
                "</voice> </speak>";

        return ssml.replace("{{ ReplaceText }}",text);
    }
}
