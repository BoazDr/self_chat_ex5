package com.boaz.dragonski.self_chat_ex5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigurationActivity extends AppCompatActivity {
    public static final String USER = "Name";
    public static final String PREFERENCES = "User Setting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        final Button skipButton = findViewById(R.id.skip_button);
        final Button nameButton = findViewById(R.id.name_button);
        final EditText editText = findViewById(R.id.enter_name);
        SharedPreferences sharedPrefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String restoredUserName = sharedPrefs.getString(USER, null);
        if (restoredUserName != null){
            startSelfChat();
            return;
        }
        nameButton.setVisibility(View.INVISIBLE);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelfChat();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence str, int start, int count, int past) {}
            @Override
            public void afterTextChanged(Editable editable) {}
            @Override
            public void onTextChanged(CharSequence str, int start, int pre, int count) {
                if (str.toString().equals("")){
                    nameButton.setVisibility(View.INVISIBLE);
                }
                else {
                    nameButton.setVisibility(View.VISIBLE);
                }
            }
        });
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(USER, editText.getText().toString());
                editor.apply();
                startSelfChat();
            }
        });
    }
    private void startSelfChat(){
        Intent intent = new Intent(ConfigurationActivity.this, SelfChatActivity.class);
        startActivity(intent);
        finish();
    }
}
