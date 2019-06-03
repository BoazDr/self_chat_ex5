package com.boaz.dragonski.self_chat_ex5;

import java.util.Collections;
import java.util.Comparator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AllMessages {
    private MessagesDataAccess msgDao;
    private FirebaseFirestore fireStore;
    public static final String FIRESTORE_MSGS = "fsMessages";
    private static AllMessages REPO = null;
    private AllMessages(Context context) {
        fireStore = FirebaseFirestore.getInstance();
        LocalDB localDB = Room.databaseBuilder(context, LocalDB.class, "messages").build();
        msgDao = localDB.messagesDataAccess();
        fireStore.collection(FIRESTORE_MSGS).get().addOnCompleteListener(ApplicationExe.getApp().getWorkerThread(),new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> tasks) {
                                if (tasks.getResult() != null && tasks.isSuccessful()) {
                                    ArrayList<OneMessage> messages = new ArrayList<>();
                                    for (QueryDocumentSnapshot querySnapshot : tasks.getResult()) {
                                        OneMessage msg = querySnapshot.toObject(OneMessage.class);
                                        messages.add(msg);
                                    }
                                    Collections.sort(messages, new Comparator<OneMessage>() {
                                        @Override
                                        public int compare(OneMessage msg1, OneMessage msg2) {
                                            return msg1.getTimestamp().compareTo(msg2.getTimestamp());
                                        }
                                    });
                                    msgDao.clear();
                                    OneMessage[] msgList = new OneMessage[messages.size()];
                                    insertMessages(messages.toArray(msgList));
                                }
                            }
                        });
    }
    public void deleteMessage(final OneMessage message) {
        ApplicationExe.getApp().getWorkerThread().execute(new Runnable() {
            @Override
            public void run() {
                msgDao.delete(message);
                fireStore.collection(FIRESTORE_MSGS).document(String.valueOf(message.getId())).delete();
            }
        });
    }
    public void insertMessages(final OneMessage... messages) {
        ApplicationExe.getApp().getWorkerThread().execute(new Runnable() {
            @Override
            public void run() {
                msgDao.insertMessages(messages);
                for (OneMessage message : messages) {
                    fireStore.collection(FIRESTORE_MSGS).document(String.valueOf(message.getId())).set(message);
                }
            }
        });
    }
    public static synchronized AllMessages getRepo(Context context) {
        if (REPO == null) {
            REPO = new AllMessages(context);
        }
        return REPO;
    }
    public LiveData<List<OneMessage>> getMessagesData() {
        return msgDao.getMessages();
    }
}
