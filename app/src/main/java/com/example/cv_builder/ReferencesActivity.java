package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class ReferencesActivity extends AppCompatActivity {
    private TextInputEditText nameEditText, positionEditText, companyEditText, emailEditText, phoneEditText, relationshipEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        // Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize views
        nameEditText = findViewById(R.id.referenceNameEditText);
        positionEditText = findViewById(R.id.referencePositionEditText);
        companyEditText = findViewById(R.id.referenceCompanyEditText);
        emailEditText = findViewById(R.id.referenceEmailEditText);
        phoneEditText = findViewById(R.id.referencePhoneEditText);
        relationshipEditText = findViewById(R.id.referenceRelationshipEditText);

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
        nameEditText.setText(sharedPreferences.getString("reference_name", ""));
        positionEditText.setText(sharedPreferences.getString("reference_position", ""));
        companyEditText.setText(sharedPreferences.getString("reference_company", ""));
        emailEditText.setText(sharedPreferences.getString("reference_email", ""));
        phoneEditText.setText(sharedPreferences.getString("reference_phone", ""));
        relationshipEditText.setText(sharedPreferences.getString("reference_relationship", ""));
    }

    private void validateAndSaveData() {
        String name = nameEditText.getText().toString().trim();
        String position = positionEditText.getText().toString().trim();
        String company = companyEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String relationship = relationshipEditText.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Reference name is required");
            nameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(position)) {
            positionEditText.setError("Position is required");
            positionEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(company)) {
            companyEditText.setError("Company is required");
            companyEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            phoneEditText.setError("Phone number is required");
            phoneEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(relationship)) {
            relationshipEditText.setError("Professional relationship is required");
            relationshipEditText.requestFocus();
            return;
        }

        // Save data if validation passes
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("reference_name", name);
        editor.putString("reference_position", position);
        editor.putString("reference_company", company);
        editor.putString("reference_email", email);
        editor.putString("reference_phone", phone);
        editor.putString("reference_relationship", relationship);
        editor.apply();

        Toast.makeText(this, "Reference details saved successfully", Toast.LENGTH_SHORT).show();
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