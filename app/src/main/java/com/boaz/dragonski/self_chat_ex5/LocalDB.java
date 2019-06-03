package com.boaz.dragonski.self_chat_ex5;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

@Database(entities = {OneMessage.class}, version = 1)
public abstract class LocalDB extends RoomDatabase {
    public abstract MessagesDataAccess messagesDataAccess();
}
