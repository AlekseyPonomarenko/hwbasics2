package ru.ponomarenko.hwbasics2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            NotesFragment notesFragment = new NotesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_edit, NoteEditFragment.newInstance(1))
                    .commit();

        } else {
            NotesFragment notesFragment = new NotesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, notesFragment)
                    .commit();
        }

    }
}