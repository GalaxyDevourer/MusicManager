package com.chmnu.groupmanager.models.entities.http;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    public Integer id;
    public String status;
    public String title;

    public Task() {};

    public Task(Integer id, String status, String title) {
        this.id = id;
        this.status = status;
        this.title = title;
    }

    public Task(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);

        id = Integer.parseInt(data[0]);
        status = data[1];
        title = data[2];
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString () {
        return "Task №" + id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { id.toString(), status, title });
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
