package com.boaz.dragonski.self_chat_ex5;

import java.util.List;
import android.arch.persistence.room.Insert;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Delete;


@Dao
public interface MessagesDataAccess {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessages(OneMessage... msgs);
    @Query("SELECT * from allMessages")
    LiveData<List<OneMessage>> getMessages();
    @Query("DELETE from allMessages")
    void clear();
    @Delete
    void delete(OneMessage msg);
}
