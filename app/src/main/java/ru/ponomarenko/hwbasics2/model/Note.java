package ru.ponomarenko.hwbasics2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
 * Структура заметки
 */
public class Note implements Parcelable {

    private String id = "";            //Индекс записи
    private String name = "";           //Название
    private String description = "";    //Описание

    private LocalDateTime creationDate; //Дата создания
    private LocalDateTime updateDate;   //Дата обновления

    public Note() {

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        this.id = randomUUIDString;
        this.creationDate = LocalDateTime.now();

    }

    public Note(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setUpdateDate() {
        this.updateDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Note(Parcel parcel){
        id = parcel.readString();
        name = parcel.readString();
        description = parcel.readString();
        creationDate = (LocalDateTime)parcel.readSerializable();
        updateDate = (LocalDateTime)parcel.readSerializable();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(getId());
        parcel.writeString(getName());
        parcel.writeString(getDescription());
        parcel.writeSerializable(getCreationDate());
        parcel.writeSerializable(getUpdateDate());

    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }
        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
