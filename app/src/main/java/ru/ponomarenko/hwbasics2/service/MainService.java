package ru.ponomarenko.hwbasics2.service;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.NotesFragment;
import ru.ponomarenko.hwbasics2.R;
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

    /**
     * Удаление заметки с постобработкой обновления списка.
     * Перед удалениеним  - диалог - Да/Нет
     * После удаления Snackbar с кнопкой "восстановить"
     *
     * @param note заметка
     * @param view контекст привязки Snackbar
     * @param view контекст вывода AlertDialog
     * @param notifyItemRemoved операция для обновления списка при удалении
     */
    public void deleteNote(Note note, View view, Context context, MyPrimitivePostOperation notifyItemRemoved) {

        //Диалог вопрос
        new AlertDialog.Builder(context)

                .setTitle("Удаление заметки!")
                .setMessage(note.getName())
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //старая позиция
                        int position = noteRepo.getIndexById(note.getId());

                        //Удаляем
                        noteRepo.delete(note.getId());

                        //Вывод сообщения
                        Snackbar.make(view,  "Удалено: " + note.getName(),  Snackbar.LENGTH_SHORT)
                                .setAction("Восстановить", view1 -> {

                                    //Восстанавливаем
                                    noteRepo.createOrUpdate(note);
                                    notesFragment.listAdapter.notifyItemRemoved(noteRepo.getIndexById(note.getId()));

                                })
                                .show();

                        if (notesFragment!=null) {
                           notesFragment.listAdapter.notifyItemRemoved(position);
                        }


                        //Постобработка
                        if (notifyItemRemoved != null) {
                            notifyItemRemoved.start();
                        }

                    }
                })
                .setNeutralButton("Отмена", null)
                .show();
    }

    //Для обновления списка
    private NotesFragment notesFragment;

    public NotesFragment getNotesFragment() {
        return notesFragment;
    }

    public void setNotesFragment(NotesFragment notesFragment) {
        this.notesFragment = notesFragment;
    }
}
