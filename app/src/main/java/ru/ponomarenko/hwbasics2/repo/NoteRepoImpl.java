package ru.ponomarenko.hwbasics2.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import ru.ponomarenko.hwbasics2.model.Note;

public class NoteRepoImpl implements NoteRepo {

    private ArrayList<Note> dataList;

    public NoteRepoImpl(ArrayList<Note> noteList) {
        this.dataList = noteList;
    }

    @Override
    public Note get(String uid) {

        Note note1 = dataList.stream().filter(n -> n.getId().equals(uid)).findFirst().orElse(null);
        return note1;
    }

    @Override
    public int getIndexById(String uid) {

        Note note1 = dataList.stream().filter(n -> n.getId().equals(uid)).findFirst().orElse(null);
        if (note1==null) return -1;
        return dataList.indexOf(note1);

    }


    @Override
    public Note getByIndex(int index) {
        return dataList.get(index);
    }

    @Override
    public int createOrUpdate(Note note) {


        Note noteOld = get(note.getId());

        if (noteOld == null) {
            dataList.add(note);
        } else {
            delete(note.getId());
            note.setUpdateDate();
            dataList.add(note);
        }

        return dataList.indexOf(note);

    }


    @Override
    public void delete(String uid) {

        Note note1 = get(uid);
        int index = dataList.indexOf(note1);
        dataList.remove(index);

    }

    public ArrayList<Note> getData() {
        return (ArrayList<Note>) dataList.clone();
    }
}
