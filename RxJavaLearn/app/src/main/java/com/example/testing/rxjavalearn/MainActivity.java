package com.example.testing.rxjavalearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.testing.rxjavalearn.operators.CreateOperatorFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //转化符
        //getSupportFragmentManager().beginTransaction()
        //    .add(new ConvertFragment(), "convertFragment")
        //    .commit();

        //retrofit结合
//    getSupportFragmentManager().beginTransaction()
//        .add(new RetrofitRxFragment(), "convertFragment")
//        .commit();


        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new CreateOperatorFragment(), "convertFragment")
                .commit();

    }

}
