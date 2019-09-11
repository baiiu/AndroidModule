package com.example.testing.testingexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void sayHello(View v) {
    TextView textView = (TextView) findViewById(R.id.textView);
    EditText editText = (EditText) findViewById(R.id.editText);
    textView.setText("Hello, " + editText.getText().toString() + "!");
  }
}
