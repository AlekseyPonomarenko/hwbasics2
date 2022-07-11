package ru.ponomarenko.hwbasics2;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.ponomarenko.hwbasics2.model.Note;

public class NotesFragment extends Fragment {

    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMainList(view);
    }

    private void initMainList(View view) {

        LinearLayout linearLayout = (LinearLayout) view;
        List<Note> demo = Note.getDemo();
        for (Note note : demo) {

            View itemView = getLayoutInflater().inflate(R.layout.layout_notes_item, linearLayout, false);

            TextView textView = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_name);
            textView.setText(note.getName());

            ((CardView) itemView.findViewById(R.id.layout_notes_item_card)).setOnClickListener(view1 -> {
                showNoteEdit(note.getId());
            });

            linearLayout.addView(itemView);

        }
    }


    private void showNoteEdit(int index) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteEditLand(index);
        } else {
            showNoteEditPortrait(index);
        }
    }


    private void showNoteEditPortrait(int index) {
        NoteEditFragment noteEditFragment = NoteEditFragment.newInstance(index);
        requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, noteEditFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showNoteEditLand(int index) {
        NoteEditFragment noteEditFragment = NoteEditFragment.newInstance(index);
        requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_edit, noteEditFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}