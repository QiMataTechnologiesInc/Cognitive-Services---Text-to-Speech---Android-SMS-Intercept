package com.example.pluralsighttexttospeechdemo;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainActivity extends AppCompatActivity {
//    private MediaPlayer _mediaPlayer;
//    private TextToSpeechClass _textToSpeech;
//
//    public Handler _handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        _mediaPlayer = new MediaPlayer();
//        _textToSpeech = new TextToSpeechClass("348c3dd18c1742b4a63ee2c9a1d385eb");
//        _handler = new Handler(){
//          @Override
//          public void handleMessage(android.os.Message msg) {
//
//          }
//        };
    }

//    private void PlayAudio(InputStream audio) throws IOException {
//        Path path = FileSystems.getDefault().getPath("speech.mp3");
//        Files.copy(audio,path);
//        _mediaPlayer.setDataSource("speech.mp3");
//        _mediaPlayer.prepare();
//        _mediaPlayer.start();
//    }
}
