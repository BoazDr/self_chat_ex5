package com.boaz.dragonski.self_chat_ex5;

import java.util.List;
import android.support.annotation.NonNull;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.AndroidViewModel;


public class ViewModelSelfChat extends AndroidViewModel {


    private final AllMessages allMessages;

    public ViewModelSelfChat(@NonNull Application app) {
        super(app);
        allMessages = AllMessages.getRepo(app);
    }

    public void deleteMessage(OneMessage message){
        allMessages.deleteMessage(message);
    }

    public void insertMessages(OneMessage... messages){
        allMessages.insertMessages(messages);
    }

    public LiveData<List<OneMessage>> getMessagesData() {
        return allMessages.getMessagesData();
    }



}
