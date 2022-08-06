package ru.ponomarenko.hwbasics2.service;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

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
     * @param refreshListOperation операция для обновления списка
     */
    public void deleteNote(Note note, View view, Context context, MyPrimitivePostOperation refreshListOperation) {

        //Диалог вопрос
        new AlertDialog.Builder(context)

                .setTitle("Удаление заметки!")
                .setMessage(note.getName())
                .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Удаляем
                        MainService.getInstance().getNoteRepo().delete(note.getId());

                        //Вывод сообщения
                        Snackbar.make(view,  "Удалено: " + note.getName(),  Snackbar.LENGTH_SHORT)
                                .setAction("Восстановить", view1 -> {

                                    //Восстанавливаем
                                    MainService.getInstance().getNoteRepo().createOrUpdate(note);

                                    //Обновляем в списке
                                    refreshListOperation.start();

                                })
                                .show();

                        //Обновляем список
                        refreshListOperation.start();

                    }
                })
                .setNeutralButton("Отмена", null)
                .show();
    }
}
