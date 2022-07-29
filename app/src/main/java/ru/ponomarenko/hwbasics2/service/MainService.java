package ru.ponomarenko.hwbasics2.service;

import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.repo.NoteRepo;
import ru.ponomarenko.hwbasics2.repo.NoteRepoImpl;

public class MainService {

    /**
     * Синглтон
     */
    private static MainService INSTANCE;

    private MainService() {

        noteList = new ArrayList<Note>();
        noteRepo = new NoteRepoImpl(noteList);

    }

    public static synchronized MainService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainService();
        }
        return INSTANCE;
    }

    /**
     * Описание общедоступных свойств
     */
    private NoteRepo noteRepo;
    private ArrayList<Note> noteList;

    /**
     * Демо генерация списка
     */
    public void generateDemoList() {
        if (!noteList.isEmpty()) {
            return;
        }
        ;

        for (int i = 1; i < 5; i++) {
            noteRepo.createOrUpdate(new Note("Тема " + i, "Детальное описание темы " + i));
        }


    }

    public NoteRepo getNoteRepo() {
        return noteRepo;
    }
}
