package ru.ponomarenko.hwbasics2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import ru.ponomarenko.hwbasics2.service.MainService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Создаем демо лист
        MainService.getInstance().generateDemoList();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NotesFragment notesFragment = new NotesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, notesFragment)
                .commit();

        /*
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            NotesFragment notesFragment = new NotesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_edit, NoteEditFragment.newInstance("1"))
                    .commit();

        } else {

        }

         */

    }
}