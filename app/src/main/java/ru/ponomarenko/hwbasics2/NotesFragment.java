package ru.ponomarenko.hwbasics2;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.ponomarenko.hwbasics2.adapter.NoteListAdapter;
import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.service.MainService;
import ru.ponomarenko.hwbasics2.service.MyPrimitiveItemClick;
import ru.ponomarenko.hwbasics2.service.MyPrimitivePostOperation;

public class NotesFragment extends Fragment {

    LinearLayout mainLinearLayout;
    RecyclerView recyclerView;

    public static NotesFragment newInstance() {
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

        ((Button) view.findViewById(R.id.btAdd)).setOnClickListener(v -> {
            showNoteEdit(new Note());
        });

        recyclerView = view.findViewById(R.id.notes_rv);
        mainLinearLayout = (LinearLayout) view;
        initMainList();
    }

    public void initMainList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        NoteListAdapter listAdapter = new NoteListAdapter(new MyPrimitiveItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                Note note = MainService.getInstance().getNoteRepo().getByIndex(position);
                showNoteEdit(note);
            }
        },
                new MyPrimitiveItemClick() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Note note = MainService.getInstance().getNoteRepo().getByIndex(position);
                        initPopupMenu(view, note);
                    }
                });
        recyclerView.setAdapter(listAdapter);
    }

    private void initPopupMenu(View view, Note note) {
        view.setOnLongClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, view);
            activity.getMenuInflater().inflate(R.menu.notes_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_popup_delete:

                            //Обновление списка
                            MyPrimitivePostOperation refreshListOperation = new MyPrimitivePostOperation() {
                                @Override
                                public void start() {
                                    initMainList();
                                }
                            };

                            //Общий метод по удалению элементов
                            MainService.getInstance().deleteNote(note, view, getContext(), refreshListOperation);

                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
            return true;
        });
    }


    private void showNoteEdit(Note item) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showNoteEditLand(item);
        } else {
            showNoteEditPortrait(item);
        }
    }


    private void showNoteEditPortrait(Note item) {

        NoteEditFragment noteEditFragment = NoteEditFragment.newInstance(item, this);
        requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, noteEditFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showNoteEditLand(Note item) {

        NoteEditFragment noteEditFragment = NoteEditFragment.newInstance(item, this);
        requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_edit, noteEditFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}