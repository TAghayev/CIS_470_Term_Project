package com.example.cis_470_term_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DreamListActivity extends AppCompatActivity {

    private Button addDreamButton;
    private ListView dreamListView;

    private UserDatabaseHelper databaseHelper;
    private ArrayList<String> dreamList;
    private ArrayAdapter<String> dreamAdapter;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_list);

        TextView welcomeMessage = findViewById(R.id.welcome_message);
        welcomeMessage.setText("Welcome to Dream Log. Log your dream and description. Your data is secure and only accessible on your account.");



        addDreamButton = findViewById(R.id.add_dream_button);
        dreamListView = findViewById(R.id.dream_list_view);

        databaseHelper = new UserDatabaseHelper(this);

        dreamList = new ArrayList<>();

        userId = getIntent().getIntExtra("USER_ID", -1);

        fetchDreams();

        addDreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addDreamIntent = new Intent(DreamListActivity.this, AddDreamActivity.class);
                addDreamIntent.putExtra("USER_ID", userId);
                startActivity(addDreamIntent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchDreams();
    }



    private void fetchDreams() {
        dreamList.clear();
        Cursor dreams = databaseHelper.getUserDreams(userId);
        while (dreams.moveToNext()) {
            String dream = dreams.getString(2) + ": " + dreams.getString(3);
            dreamList.add(dream);
        }
        dreamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dreamList);
        dreamListView.setAdapter(dreamAdapter);
    }


}

