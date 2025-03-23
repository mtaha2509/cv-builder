package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ExperienceActivity extends AppCompatActivity {
    private TextInputEditText companyEditText, positionEditText, startDateEditText, endDateEditText, responsibilitiesEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        sharedPreferences = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Load saved data
        loadSavedData();

        // Set up button click listeners
        saveButton.setOnClickListener(v -> validateAndSaveData());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadSavedData() {
        companyEditText.setText(sharedPreferences.getString("experience_company", ""));
        positionEditText.setText(sharedPreferences.getString("experience_position", ""));
        startDateEditText.setText(sharedPreferences.getString("experience_start_date", ""));
        endDateEditText.setText(sharedPreferences.getString("experience_end_date", ""));
        responsibilitiesEditText.setText(sharedPreferences.getString("experience_responsibilities", ""));
    }

    private void validateAndSaveData() {
        String company = companyEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();
        String responsibilities = responsibilitiesEditText.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(company)) {
            companyEditText.setError("Company name is required");
            companyEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(position)) {
            positionEditText.setError("Position is required");
            positionEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(startDate)) {
            startDateEditText.setError("Start date is required");
            startDateEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(endDate)) {
            endDateEditText.setError("End date is required");
            endDateEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(responsibilities)) {
            responsibilitiesEditText.setError("Key responsibilities are required");
            responsibilitiesEditText.requestFocus();
            return;
        }

        // Save data if validation passes
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("experience_company", company);
        editor.putString("experience_position", position);
        editor.putString("experience_start_date", startDate);
        editor.putString("experience_end_date", endDate);
        editor.putString("experience_responsibilities", responsibilities);
        editor.apply();

        Toast.makeText(this, "Work experience saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
} 