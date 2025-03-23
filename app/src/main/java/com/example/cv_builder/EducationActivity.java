package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class EducationActivity extends AppCompatActivity {
    private TextInputEditText institutionEditText, degreeEditText, fieldEditText, startYearEditText, endYearEditText, achievementsEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Education");
        }

        // Initialize views
        institutionEditText = findViewById(R.id.institutionEditText);
        degreeEditText = findViewById(R.id.degreeEditText);
        fieldEditText = findViewById(R.id.fieldEditText);
        startYearEditText = findViewById(R.id.startYearEditText);
        endYearEditText = findViewById(R.id.endYearEditText);
        achievementsEditText = findViewById(R.id.achievementsEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Load saved data
        loadSavedData();

        // Set up button click listeners
        saveButton.setOnClickListener(v -> validateAndSaveData());
        cancelButton.setOnClickListener(v -> finish());

        // Set up year validation
        setupYearValidation();
    }

    private void setupYearValidation() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        startYearEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String year = startYearEditText.getText().toString();
                if (!year.isEmpty() && (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > currentYear)) {
                    startYearEditText.setError("Please enter a valid year");
                }
            }
        });

        endYearEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String year = endYearEditText.getText().toString();
                if (!year.isEmpty() && (Integer.parseInt(year) < 1900 || Integer.parseInt(year) > currentYear + 10)) {
                    endYearEditText.setError("Please enter a valid year");
                }
            }
        });
    }

    private void loadSavedData() {
        institutionEditText.setText(sharedPreferences.getString("education_institution", ""));
        degreeEditText.setText(sharedPreferences.getString("education_degree", ""));
        fieldEditText.setText(sharedPreferences.getString("education_field", ""));
        startYearEditText.setText(sharedPreferences.getString("education_start_year", ""));
        endYearEditText.setText(sharedPreferences.getString("education_end_year", ""));
        achievementsEditText.setText(sharedPreferences.getString("education_achievements", ""));
    }

    private void validateAndSaveData() {
        String institution = institutionEditText.getText().toString().trim();
        String degree = degreeEditText.getText().toString().trim();
        String field = fieldEditText.getText().toString().trim();
        String startYear = startYearEditText.getText().toString().trim();
        String endYear = endYearEditText.getText().toString().trim();
        String achievements = achievementsEditText.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(institution)) {
            institutionEditText.setError("Institution name is required");
            institutionEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(degree)) {
            degreeEditText.setError("Degree is required");
            degreeEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(field)) {
            fieldEditText.setError("Field of study is required");
            fieldEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(startYear)) {
            startYearEditText.setError("Start year is required");
            startYearEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(endYear)) {
            endYearEditText.setError("End year is required");
            endYearEditText.requestFocus();
            return;
        }

        // Save data if validation passes
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("education_institution", institution);
        editor.putString("education_degree", degree);
        editor.putString("education_field", field);
        editor.putString("education_start_year", startYear);
        editor.putString("education_end_year", endYear);
        editor.putString("education_achievements", achievements);
        editor.apply();

        Toast.makeText(this, "Education details saved successfully", Toast.LENGTH_SHORT).show();
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