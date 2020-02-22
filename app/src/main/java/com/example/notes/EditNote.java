package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditNote extends AppCompatActivity {

    private String id;
    private DatabaseHelper database;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        note = findViewById(R.id.updateNote);
        database = new DatabaseHelper(this);
        id = getIntent().getStringExtra("id");

        if ( !id.equals("null") ) {

            final String str = getIntent().getStringExtra("note").toString();
            note.setText(str);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        final String noteString = note.getText().toString();

        if ( id.equals("null") ) {

            database.insert(noteString);

        }
        else {

            database.update(id, noteString);

        }

    }
}
