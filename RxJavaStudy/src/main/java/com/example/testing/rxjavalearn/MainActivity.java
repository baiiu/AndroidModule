package com.example.testing.rxjavalearn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testing.rxjavalearn.fragments.ResearchFragment;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
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
                .add(android.R.id.content, getFragment(), "operator")
                .commit();

        //Observable.just(0);

    }


    private Fragment getFragment() {

        return new ResearchFragment();

        //return new CreateOperatorFragment();

        //return new TransformOperatorsFragment();

        //return new FilterOperatorsFragment();

        //return new CombiningOperatorsFragment();

        //return new ErrorHandingFragment();

        //return new UtilityOperatorsFragment();

        //return new ConditionalBooleanOperatorsFragment();

        //return new AggregateOperatorsFragment();

        //return new ConnectOperatorsFragment();

        //return new ErrorTestFragment();

        //return new CustomOperatorsFragment();


        //return new SubjectFragment();


        //return new ErrorTestFragment();

        //return new TransformerTestFragment();
    }

}
