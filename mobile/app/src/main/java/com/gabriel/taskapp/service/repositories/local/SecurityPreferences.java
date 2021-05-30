package com.gabriel.taskapp.service.repositories.local;

import android.content.Context;
import android.content.SharedPreferences;

import static com.gabriel.taskapp.service.constants.PersonConstants.PERSON_EMAIL;
import static com.gabriel.taskapp.service.constants.PersonConstants.PERSON_TOKEN;

public class SecurityPreferences {
    private final SharedPreferences mPreferences;

    public SecurityPreferences(Context context){
        mPreferences = context.getSharedPreferences("taskShared", Context.MODE_PRIVATE);
    }

    public void store(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }

    public String get(String key) {
        return mPreferences.getString(key, "");
    }

    public Long getLong(String key) {
        return mPreferences.getLong(key, 0L);
    }

    public void storeLong(String key, Long value) {
        mPreferences.edit().putLong(key, value).apply();
    }

    public void logout() {
        remove(PERSON_TOKEN);
        remove(PERSON_EMAIL);
    }
}