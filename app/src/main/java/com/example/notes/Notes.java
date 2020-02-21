package com.example.notes;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.Modules.Note;
import com.example.notes.RecylerView.NotesRecylerView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;


public class Notes extends Fragment {

//    private Button button, show;
//    private EditText edittext;
    private DatabaseHelper database;
    private List<Note> notes;
    RecyclerView notesView;
    NotesRecylerView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        database = new DatabaseHelper(getActivity());
        notes = new ArrayList<>();
        notesView = (RecyclerView) v.findViewById(R.id.notesRecylerView);
        notes.add(new Note(0, "hello"));
        notes.add(new Note(1, "hello"));
        notes.add(new Note(2, "hello"));
        notes.add(new Note(3, "hello"));
        notes.add(new Note(4, "hello"));
        adapter = new NotesRecylerView(getActivity(), notes);
        notesView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        notesView.setAdapter(adapter);


//        edittext = (EditText)v.findViewById(R.id.text);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String str = edittext.getText().toString();
//                Boolean err = database.insert(str);
//                if ( err ) {
//                    Toast.makeText(getActivity(), "Fail to insert data", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringBuffer str = new StringBuffer();
//                Cursor res = database.getAllData();
//
//                if ( res.getCount() == 0 ) {
//                    Toast.makeText(getActivity(), "No data in the database", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                while ( res.moveToNext() ) {
//                    str.append("ID: " + res.getString(0) + "\n");
//                    str.append("Note: " + res.getString(1) + "\n\n");
//                }
//
//                Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
//            }
//        });

        return v;
    }
}
