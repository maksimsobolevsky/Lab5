package com.example.lab5.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5.R;

public class FormActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String COMMENT = "comment";

    private TextView name;
    private TextView surname;
    private TextView comment;
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        comment = findViewById(R.id.commentEditText);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);

        save.setOnClickListener(view -> {

            Intent intent = new Intent();
            intent.putExtra(NAME, name.getText().toString());
            intent.putExtra(SURNAME, surname.getText().toString());
            intent.putExtra(COMMENT, comment.getText().toString());

            setResult(RESULT_OK, intent);
            finish();
        });

        cancel.setOnClickListener(view ->{
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

}
