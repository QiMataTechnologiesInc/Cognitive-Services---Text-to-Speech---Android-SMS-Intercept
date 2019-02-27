package com.example.pluralsighttexttospeechdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.util.Log;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class SMSListener extends BroadcastReceiver {
    private TextToSpeechClass _textToSpeech;
    private MediaPlayer _mediaPlayer;
    HandlerThread handlerThread = new HandlerThread("backgroundThread");

    public SMSListener() {
        _textToSpeech = new TextToSpeechClass("{{ Your API Key }}");
        _mediaPlayer = new MediaPlayer();
        if (!handlerThread.isAlive())
            handlerThread.start();
    }

    @Override

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;

            if (bundle != null) {
                try {
                    Object[] pdus = (Object[])bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];

                    for (int i=0; i<msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        final String msgBody = msgs[i].getMessageBody();
                        final String filePath = context.getFilesDir().getPath().toString() + "speech.mp3";
                        Handler mainHandler = new Handler(handlerThread.getLooper());
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    InputStream audio = _textToSpeech.ConvertTextToAudio(msgBody);
                                    PlayAudio(audio,filePath);
                                } catch (IOException e) {
                                    Log.e("IOException",e.getMessage());
                                } catch (UnirestException e) {
                                    Log.e("UnirestException",e.getMessage());
                                }
                            }
                        };
                        mainHandler.post(myRunnable);
                    }

                } catch (Exception e) {
                    Log.e("GenericException",e.getMessage());
                }
            }
        }
    }


    private void PlayAudio(InputStream audio, String filePath) throws IOException {
        Path path = FileSystems.getDefault().getPath(filePath);
        Files.copy(audio,path,  StandardCopyOption.REPLACE_EXISTING);
        _mediaPlayer.setDataSource(filePath);
        _mediaPlayer.prepare();
        _mediaPlayer.start();
    }
}
