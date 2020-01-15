package com.baiiu.hookapp;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.baiiu.hookapp.startActivity.StartActivityHook;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                StartActivityHook.hook(MainActivity.this);
                startActivity(
                        new Intent(MainActivity.this, SecondActivity.class)
                );
            }
        });

    }
}
