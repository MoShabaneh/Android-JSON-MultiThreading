package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    ListView listView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);

        saveInfo("{'name':'Shabaneh','age':22,'jobTitle':'Software Engineer'}");

        button.setOnClickListener(v -> {
            // Set list view with info
            Info info = Info.readInfo(getSharedPreferences("shared preferences", MODE_PRIVATE));
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("Name: "+info.getName());
            adapter.add("Age: "+info.getAge());
            adapter.add("Job Title: "+info.getJobTitle());
            listView.setAdapter(adapter);
            listView.setVisibility(listView.getVisibility() == ListView.VISIBLE ? ListView.GONE : ListView.VISIBLE);

            // Set image view with photo with progress bar while loading
            Photo photo = new Photo("Photo", "https://www.topgear.com/sites/default/files/2024/10/1-BMW-M4-review-2024-UK.jpg");

            progressBar.setVisibility(ProgressBar.VISIBLE);
            new Thread(() -> {
                photo.run();
                new Handler(Looper.getMainLooper()).post(() -> {
                    imageView.setImageBitmap(photo.getImage());
                    imageView.setVisibility(ImageView.VISIBLE);
                    progressBar.setVisibility(ProgressBar.GONE);
                });
            }).start();
        });





    }
    private void saveInfo(String json){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Info", json);
        editor.apply();
    }

}
