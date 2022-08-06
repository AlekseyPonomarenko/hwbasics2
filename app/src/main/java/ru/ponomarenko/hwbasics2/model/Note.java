package ru.ponomarenko.hwbasics2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Создайте класс данных со структурой заметок: название заметки, описание заметки, дата создания и т. п.
 * Структура заметки
 */
public class Note {

    private Integer id  = 0;            //Индекс записи
    private String name = "";           //Название
    private String description = "";    //Описание
    private String date = "";           //Дата создания, это пока строка. Позже разберемся


    public Note(Integer id, String name, String description) {
        this.id = id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }


    public void setDate(String date) {
        this.date = date;
    }


    /**
     * Статический демо метод, для тестового получения списка.
     * @return
     */
    public static List<Note> getDemo(){
        List<Note> array = new ArrayList<>();

        array.add(new Note(1, "Тема 1", "Подробное описание темы 1"));
        array.add(new Note(2, "Тема 2", "Подробное описание темы 2"));
        array.add(new Note(3, "Тема 3", "Подробное описание темы 3"));
        array.add(new Note(4, "Тема 4", "Подробное описание темы 4"));
        array.add(new Note(5, "Тема 5", "Подробное описание темы 5"));

        return array;
    }

}
