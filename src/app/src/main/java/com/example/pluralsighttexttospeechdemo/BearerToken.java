package com.example.pluralsighttexttospeechdemo;

import java.util.Calendar;
import java.util.Date;

public class BearerToken {
    static final long OneMinuteInMills = 60000;

    private String _token;
    private Date _validUntil;

    public BearerToken(String token) {
        _token = token;

        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        _validUntil = new Date(t + (9 * OneMinuteInMills));
    }

    public Boolean IsValid() {
        return _validUntil.after(new Date());
    }

    public String HeaderValue() {
        return "Bearer " + _token;
    }
}
