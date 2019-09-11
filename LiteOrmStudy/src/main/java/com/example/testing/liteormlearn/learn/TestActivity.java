package com.example.testing.liteormlearn.learn;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testing.liteormlearn.R;
import com.example.testing.liteormlearn.model.me.ModelA;
import com.example.testing.liteormlearn.orm.UOrm;

/**
 * author: baiiu
 * date: on 16/5/18 17:25
 * description:
 */
public class TestActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ModelA modelA = new ModelA("en", "小明", 21, "sky");
    UOrm.INSTANCE.save(modelA);



  }
}
