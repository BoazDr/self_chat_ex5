package com.boaz.dragonski.self_chat_ex5;

import android.arch.lifecycle.Observer;
import java.util.List;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;


public class SelfChatActivity extends FragmentActivity implements RecycleViewUtils.OnLongClickMessageListener, MessagesInfo.listenerToDelete {
    private RecyclerView recyclerView;
    private MessagesInfo messageInfo;
    private ViewModelSelfChat viewModel;

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_self_chat);
        final EditText editText = findViewById(R.id.editText);
        viewModel = ViewModelProviders.of(this).get(ViewModelSelfChat.class);
        recyclerView = findViewById(R.id.recycler_view);
        final RecycleViewUtils.RowAdapter rowAdapter = new RecycleViewUtils.RowAdapter(this);

        SharedPreferences sharedPrefs = getSharedPreferences(ConfigurationActivity.PREFERENCES, MODE_PRIVATE);
        String restoredName = sharedPrefs.getString(ConfigurationActivity.USER, null);
        if (restoredName != null){
            String helloUserText = "Hello " + restoredName + "!";
            TextView userName = findViewById(R.id.name);
            userName.setText(helloUserText);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rowAdapter);

        viewModel.getMessagesData().observe(this, new Observer<List<OneMessage>>() {
            @Override
            public void onChanged(@Nullable List<OneMessage> messages) {
                if (messages == null)
                    return;

                rowAdapter.setMessages(messages);
                recyclerView.scrollToPosition(messages.size() - 1);
            }
        });

        findViewById(R.id.send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(SelfChatActivity.this, "Empty message? Realy?! try again...", Toast.LENGTH_SHORT).show();
                    return;
                }
                OneMessage msg = new OneMessage();
                msg.setText(message);
                viewModel.insertMessages(msg);
                editText.getText().clear();
            }
        });
    }
    @Override
    public void onLongClickedOnMessage(OneMessage msg) {
        messageInfo = new MessagesInfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable("message", msg);
        messageInfo.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.message_frame, messageInfo).addToBackStack(null).commit();
    }
    @Override
    public void onDelete(OneMessage msg) {
        viewModel.deleteMessage(msg);
        getSupportFragmentManager().beginTransaction().remove(messageInfo).commit();
    }
}
