package com.boaz.dragonski.self_chat_ex5;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "allMessages")
public class OneMessage implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String deviceCreatedMessage;
    private String timestamp;
    private String text;
    protected OneMessage(Parcel parcel) {
        id = parcel.readLong();
        timestamp = parcel.readString();
        text = parcel.readString();
        deviceCreatedMessage = parcel.readString();
    }
    public OneMessage() {
        deviceCreatedMessage = Build.BRAND + " " + Build.MODEL;
        Date date = new Date(System.currentTimeMillis());
        id = date.hashCode();
        DateFormat dateInstance = SimpleDateFormat.getDateInstance();
        timestamp = dateInstance.format(date);

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(text);
        dest.writeString(deviceCreatedMessage);
        dest.writeString(timestamp);

    }

    public static final Creator<OneMessage> CREATOR = new Creator<OneMessage>() {
        @Override
        public OneMessage createFromParcel(Parcel parcel) {
            return new OneMessage(parcel);
        }
        @Override
        public OneMessage[] newArray(int size) {
            return new OneMessage[size];
        }
    };
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getDeviceCreatedMessage() {
        return deviceCreatedMessage;
    }
    public void setDeviceCreatedMessage(String deviceCreatedMessage) {
        this.deviceCreatedMessage = deviceCreatedMessage;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
