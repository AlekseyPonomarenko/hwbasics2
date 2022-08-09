package ru.ponomarenko.hwbasics2.repo;

import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.model.Note;

/**
 * Интерфейс репозитория
 */
public interface NoteRepo {

    Note get(String uid);

    int getIndexById(String uid);

    Note getByIndex(int index);

    int createOrUpdate(Note note);

    void delete(String uid);

    ArrayList<Note> getData();

    void setData(ArrayList<Note> data);

}
