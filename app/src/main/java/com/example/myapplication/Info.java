package com.example.myapplication;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Info {
    private String name;
    private int age ;
    private String jobTitle;

    public Info(String name, int age, String jobTitle) {
        this.name = name;
        this.age = age;
        this.jobTitle = jobTitle;
    }
    public Info() {

    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public static Info readInfo(SharedPreferences PrefNAME){
        Gson gson = new Gson();
        String json = PrefNAME.getString("Info", "");
        Info info = gson.fromJson(json, Info.class);
      return info;
    }

}
