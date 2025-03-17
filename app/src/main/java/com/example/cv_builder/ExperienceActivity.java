package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class ExperienceActivity extends AppCompatActivity {
    private TextInputEditText companyEditText, positionEditText, startDateEditText, 
                             endDateEditText, responsibilitiesEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Work Experience");
        }

        // Initialize views
        companyEditText = findViewById(R.id.companyEditText);
        positionEditText = findViewById(R.id.positionEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);
        responsibilitiesEditText = findViewById(R.id.responsibilitiesEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CV_DATA", MODE_PRIVATE);

        // Load saved data
        loadSavedData();

        // Set up button click listeners
        saveButton.setOnClickListener(v -> saveData());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadSavedData() {
        companyEditText.setText(sharedPreferences.getString("experience_company", ""));
        positionEditText.setText(sharedPreferences.getString("experience_position", ""));
        startDateEditText.setText(sharedPreferences.getString("experience_start_date", ""));
        endDateEditText.setText(sharedPreferences.getString("experience_end_date", ""));
        responsibilitiesEditText.setText(sharedPreferences.getString("experience_responsibilities", ""));
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("experience_company", companyEditText.getText().toString());
        editor.putString("experience_position", positionEditText.getText().toString());
        editor.putString("experience_start_date", startDateEditText.getText().toString());
        editor.putString("experience_end_date", endDateEditText.getText().toString());
        editor.putString("experience_responsibilities", responsibilitiesEditText.getText().toString());
        editor.apply();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 