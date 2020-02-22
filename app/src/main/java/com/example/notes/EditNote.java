package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNote extends AppCompatActivity {

    private String id;
    private Button backToNotes;
    private DatabaseHelper database;
    private EditText note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        note = findViewById(R.id.updateNote);
        backToNotes = findViewById(R.id.backToNotes);
        database = new DatabaseHelper(this);
        id = getIntent().getStringExtra("id");

        if ( !id.equals("null") ) {

            final String str = getIntent().getStringExtra("note").toString();
            note.setText(str);

        }

        backToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToNotesActivity();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        final String noteString = note.getText().toString();

        if ( noteString.trim().equals("") ) {

            if ( !id.equals("null") ) {
                database.delete(id);
            }
            return;

        }

        if ( id.equals("null") ) {

            database.insert(noteString);

        }
        else {
                database.update(id, noteString);

        }

    }

    public void BackToNotesActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
