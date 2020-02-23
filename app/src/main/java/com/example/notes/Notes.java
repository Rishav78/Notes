package com.example.notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notes.Database.DatabaseHelper;
import com.example.notes.Modules.Note;
import com.example.notes.RecylerView.NotesRecylerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Notes extends Fragment {

    private DatabaseHelper database;
    private List<Note> notes;
    private RecyclerView notesView;
    private NotesRecylerView adapter;
    private FloatingActionButton addNotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        addNotes = v.findViewById(R.id.newNote);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditNoteActivity();
            }
        });

        database = new DatabaseHelper(getActivity());
        notes = new ArrayList<>();
        notesView = v.findViewById(R.id.notesRecylerView);
        notesView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        new ItemTouchHelper(itemTouchHelperSimpleCallback).attachToRecyclerView(notesView);
        retrieveData();

        return v;
    }

    public void startEditNoteActivity() {
        Intent intent = new Intent(getActivity(), EditNote.class);
        intent.putExtra("id", "null");
        startActivity(intent);
    }

    public void retrieveData() {

        notes.clear();

        StringBuffer str = new StringBuffer();
        Cursor res = database.getAllData_table1();

        while ( res.moveToNext() ) {
            int ID = Integer.parseInt(res.getString(0));
            String NOTE = res.getString(1), UPDATEDAT = res.getString(2);
            notes.add(new Note(ID, NOTE, UPDATEDAT));
        }

        adapter = new NotesRecylerView(getActivity(), notes);
        notesView.setAdapter(adapter);

        if ( notes.size() == 0 ) {
            Toast.makeText(getActivity(), "No data in the database", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveData();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperSimpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    database.delete_table1(Integer.toString(notes.get(viewHolder.getAdapterPosition()).getId()));
                    notes.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                }
            };
}
