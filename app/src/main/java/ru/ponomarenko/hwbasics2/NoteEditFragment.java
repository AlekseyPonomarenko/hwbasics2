package ru.ponomarenko.hwbasics2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ponomarenko.hwbasics2.model.Note;


public class NoteEditFragment extends Fragment {

    static final String ARG_INDEX_NOTE = "indexNote";

    public static NoteEditFragment newInstance(int id) {

        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX_NOTE, id);
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
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {

            int index = arguments.getInt(ARG_INDEX_NOTE);
            for (Note note : Note.getDemo()) {
                if (note.getId().equals(index)) {
                    ((TextView) view.findViewById(R.id.details_tv_name)).setText(note.getName());
                    ((TextView) view.findViewById(R.id.details_tv_description)).setText(note.getDescription());
                }
            }


        }

    }
}