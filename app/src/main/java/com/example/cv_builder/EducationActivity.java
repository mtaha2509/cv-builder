package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
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
        sharedPreferences = getSharedPreferences("CV_DATA", MODE_PRIVATE);

        // Load saved data
        loadSavedData();

        // Set up button click listeners
        saveButton.setOnClickListener(v -> saveData());
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

    private void saveData() {
        // Validate years before saving
        String startYear = startYearEditText.getText().toString();
        String endYear = endYearEditText.getText().toString();
        
        if (!startYear.isEmpty() && !endYear.isEmpty()) {
            if (Integer.parseInt(endYear) < Integer.parseInt(startYear)) {
                endYearEditText.setError("End year must be after start year");
                return;
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("education_institution", institutionEditText.getText().toString());
        editor.putString("education_degree", degreeEditText.getText().toString());
        editor.putString("education_field", fieldEditText.getText().toString());
        editor.putString("education_start_year", startYear);
        editor.putString("education_end_year", endYear);
        editor.putString("education_achievements", achievementsEditText.getText().toString());
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