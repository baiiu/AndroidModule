package com.example.testing.liteormlearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    UOrm.INSTANCE.getLiteOrm().save(modelA);
  }
}
