package com.chmnu.groupmanager.models.entities.http;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public Integer id;
    public String name;
    public String username;

    public User () {};

    public User(Integer id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public User(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);

        id = Integer.parseInt(data[0]);
        name = data[1];
        username = data[2];
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return username + "`s TODO";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { id.toString(), name, username });
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
