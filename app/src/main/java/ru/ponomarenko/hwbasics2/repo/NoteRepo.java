package ru.ponomarenko.hwbasics2.repo;

import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.model.Note;

/**
 * Интерфейс репозитория
 */
public interface NoteRepo {

    Note get(String uid);

    Note getByIndex(int index);

    void createOrUpdate(Note note);

    void delete(String uid);

    ArrayList<Note> getData();

}
