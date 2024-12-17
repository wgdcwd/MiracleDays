package com.example.miracledays.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.miracledays.models.Routine;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String PREFS_NAME = "miracledays_data";
    private static final String ROUTINE_KEY = "routines";

    private SharedPreferences prefs;
    private Gson gson;

    public DataManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // 루틴 목록 불러오기
    public List<Routine> getRoutines() {
        String json = prefs.getString(ROUTINE_KEY, "");
        Type listType = new TypeToken<ArrayList<Routine>>() {}.getType();
        return json.isEmpty() ? new ArrayList<>() : gson.fromJson(json, listType);
    }

    // 루틴 목록 저장
    public void saveRoutines(List<Routine> routines) {
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(routines);
        editor.putString(ROUTINE_KEY, json);
        editor.apply();
    }
}
