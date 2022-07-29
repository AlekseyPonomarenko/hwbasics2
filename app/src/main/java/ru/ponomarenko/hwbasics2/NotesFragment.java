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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.service.MainService;

public class NotesFragment extends Fragment {


    LinearLayout mainLinearLayout;

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

        mainLinearLayout = (LinearLayout) view;
        initMainList();
    }

    public void initMainList() {


        LinearLayout container = (LinearLayout) mainLinearLayout.findViewById(R.id.container);
        container.removeAllViews();

        for (Note note : MainService.getInstance().getNoteRepo().getData()) {

            View itemView = getLayoutInflater().inflate(R.layout.layout_notes_item, container, false);

            TextView tv_name = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_name);
            tv_name.setText(note.getName());

            TextView tv_create_date = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_create_date);
            tv_create_date.setText(note.getCreationDate().toString());

            TextView tv_update_date = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_update_date);
            if (note.getUpdateDate() == null) {
                tv_update_date.setText("");
            } else {
                tv_update_date.setText(note.getUpdateDate().toString());
            }


            ((CardView) itemView.findViewById(R.id.layout_notes_item_card)).setOnClickListener(view1 -> {
                showNoteEdit(note);
            });

            container.addView(itemView);

            initPopupMenu(itemView, note);

        }
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

                            MainService.getInstance().getNoteRepo().delete(note.getId());
                            initMainList();
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
/*

        NoteEditFragment noteEditFragment = NoteEditFragment.newInstance(item, this);
        requireActivity().
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_edit, noteEditFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

 */

    }


}