package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SauceMaster extends AppCompatActivity {

    EditText code;
    RelativeLayout button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sauce_master);
        getWindow().setStatusBarColor(ContextCompat.getColor(SauceMaster.this, R.color.nhcolor));

        button = findViewById(R.id.button);
        code = findViewById(R.id.code_input);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://nhentai.net/g/" + code.getText().toString();
                Intent intent = new Intent(view.getContext(), Browser.class);
                intent.putExtra("url", url);
                intent.putExtra("previousActivity", SauceMaster.class.getName());
                view.getContext().startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        // Get the class name of the previous activity
        String previousActivityClassName = getIntent().getStringExtra("previousActivity");

        if (previousActivityClassName != null) {
            try {
                // Create an Intent for the previous activity using its class name
                Class<?> previousActivityClass = Class.forName(previousActivityClassName);
                Intent intent = new Intent(SauceMaster.this, previousActivityClass);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed(); // If no previous activity specified, perform default back button behavior
        }
    }
}