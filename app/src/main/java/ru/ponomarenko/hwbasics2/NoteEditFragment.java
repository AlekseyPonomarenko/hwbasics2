package ru.ponomarenko.hwbasics2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.service.MainService;
import ru.ponomarenko.hwbasics2.service.MyPrimitivePostOperation;


public class NoteEditFragment extends Fragment {

    static final String SELECTED_NOTE = "note";

    Note note;
    EditText etName, etDescription;
    Button btSave, btDelete;
    Fragment parentForm;

    public static NoteEditFragment newInstance(Note item, Fragment parentForm) {

        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_NOTE, item);
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

        etName = view.findViewById(R.id.details_et_name);
        etDescription = view.findViewById(R.id.details_et_description);
        btDelete = view.findViewById(R.id.details_bt_delete);
        btSave = view.findViewById(R.id.details_bt_save);

        Bundle arguments = getArguments();
        if (arguments != null) {
            note = (Note) arguments.getParcelable(SELECTED_NOTE);
            etName.setText(note.getName());
            etDescription.setText(note.getDescription());
        }

        btSave.setOnClickListener(v -> {

            note.setName(etName.getText().toString());
            note.setDescription(etDescription.getText().toString());
            MainService.getInstance().getNoteRepo().createOrUpdate(note);
            refreshList();
            closeForm();

        });




        btDelete.setOnClickListener(v -> {

            //Обновление списка
            MyPrimitivePostOperation refreshListOperation = new MyPrimitivePostOperation() {
                @Override
                public void start() {
                    refreshList();
                    closeForm();
                }
            };

            //Общий метод по удалению элементов
            MainService.getInstance().deleteNote(note, view, getContext(), refreshListOperation);

        });

    }


    private void refreshList() {

        if (parentForm instanceof NotesFragment) {
            ((NotesFragment) parentForm).initMainList();
        }

    }


    private void closeForm() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }


}