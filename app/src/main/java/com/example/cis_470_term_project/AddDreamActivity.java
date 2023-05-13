package com.example.cis_470_term_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDreamActivity extends AppCompatActivity {
    private EditText titleInput;
    private EditText descriptionInput;
    private Button addDreamButton;
    private UserDatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dream);
        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.description_input);
        addDreamButton = findViewById(R.id.add_dream_button);
        databaseHelper = new UserDatabaseHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);
        addDreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                if (!title.isEmpty() && !description.isEmpty()) {
                    databaseHelper.addDream(title, description, userId);
                    Toast.makeText(AddDreamActivity.this, "Dream added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddDreamActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

