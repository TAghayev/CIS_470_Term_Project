package com.example.cis_470_term_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DreamListActivity extends AppCompatActivity {

    private Button addDreamButton, searchButton;
    private ListView dreamListView;
    private EditText searchInput;

    private UserDatabaseHelper databaseHelper;
    private ArrayList<String> dreamList;
    private ArrayAdapter<String> dreamAdapter;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_list);
        searchButton = findViewById(R.id.search_button);
        searchInput = findViewById(R.id.search_input);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = searchInput.getText().toString().trim();
                searchDreams(searchTerm);
            }
        });

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


        dreamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor dreams = databaseHelper.getUserDreams(userId);
                dreams.moveToPosition(position);

                String title = dreams.getString(2);
                String date = dreams.getString(4);
                String description = dreams.getString(3);

                Intent dreamDetailIntent = new Intent(DreamListActivity.this, DreamDetailActivity.class);
                dreamDetailIntent.putExtra("DREAM_TITLE", title);
                dreamDetailIntent.putExtra("DREAM_DATE", date);
                dreamDetailIntent.putExtra("DREAM_DESCRIPTION", description);
                startActivity(dreamDetailIntent);
            }
        });

        dreamListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor dreams = databaseHelper.getUserDreams(userId);
                dreams.moveToPosition(position);

                int dreamId = dreams.getInt(0);
                String dreamTitle = dreams.getString(2);
                String dreamDescription = dreams.getString(3);

                // Show a dialog or confirmation message before deleting the dream

                // Delete the dream from the database
                databaseHelper.deleteDream(dreamId);

                // Remove the dream from the list
                dreamList.remove(position);
                dreamAdapter.notifyDataSetChanged();

                return true;
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
            String dreamTitle = dreams.getString(2);
            String dreamDescription = dreams.getString(3);
            String dreamEntry = dreamTitle + ": " + dreamDescription;
            dreamList.add(dreamEntry);
        }
        dreamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dreamList);
        dreamListView.setAdapter(dreamAdapter);
    }


    private void searchDreams(String searchTerm) {
        ArrayList<String> filteredDreams = new ArrayList<>();

        for (String dream : dreamList) {
            if (dream.toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredDreams.add(dream);
            }
        }

        dreamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredDreams);
        dreamListView.setAdapter(dreamAdapter);
    }
}