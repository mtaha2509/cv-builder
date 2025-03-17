package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ReferencesActivity extends AppCompatActivity {
    private TextInputEditText nameEditText, positionEditText, companyEditText, 
                             emailEditText, phoneEditText, relationshipEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("References");
        }

        // Initialize views
        nameEditText = findViewById(R.id.referenceNameEditText);
        positionEditText = findViewById(R.id.referencePositionEditText);
        companyEditText = findViewById(R.id.referenceCompanyEditText);
        emailEditText = findViewById(R.id.referenceEmailEditText);
        phoneEditText = findViewById(R.id.referencePhoneEditText);
        relationshipEditText = findViewById(R.id.relationshipEditText);
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
        nameEditText.setText(sharedPreferences.getString("reference_name", ""));
        positionEditText.setText(sharedPreferences.getString("reference_position", ""));
        companyEditText.setText(sharedPreferences.getString("reference_company", ""));
        emailEditText.setText(sharedPreferences.getString("reference_email", ""));
        phoneEditText.setText(sharedPreferences.getString("reference_phone", ""));
        relationshipEditText.setText(sharedPreferences.getString("reference_relationship", ""));
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("reference_name", nameEditText.getText().toString());
        editor.putString("reference_position", positionEditText.getText().toString());
        editor.putString("reference_company", companyEditText.getText().toString());
        editor.putString("reference_email", emailEditText.getText().toString());
        editor.putString("reference_phone", phoneEditText.getText().toString());
        editor.putString("reference_relationship", relationshipEditText.getText().toString());
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