package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class CertificationsActivity extends AppCompatActivity {
    private TextInputEditText certificationNameEditText, issuingOrganizationEditText, 
                             issueDateEditText, expiryDateEditText, credentialIdEditText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certifications);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Certifications");
        }

        // Initialize views
        certificationNameEditText = findViewById(R.id.certificationNameEditText);
        issuingOrganizationEditText = findViewById(R.id.issuingOrganizationEditText);
        issueDateEditText = findViewById(R.id.issueDateEditText);
        expiryDateEditText = findViewById(R.id.expiryDateEditText);
        credentialIdEditText = findViewById(R.id.credentialIdEditText);
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
        certificationNameEditText.setText(sharedPreferences.getString("certification_name", ""));
        issuingOrganizationEditText.setText(sharedPreferences.getString("certification_organization", ""));
        issueDateEditText.setText(sharedPreferences.getString("certification_issue_date", ""));
        expiryDateEditText.setText(sharedPreferences.getString("certification_expiry_date", ""));
        credentialIdEditText.setText(sharedPreferences.getString("certification_credential_id", ""));
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("certification_name", certificationNameEditText.getText().toString());
        editor.putString("certification_organization", issuingOrganizationEditText.getText().toString());
        editor.putString("certification_issue_date", issueDateEditText.getText().toString());
        editor.putString("certification_expiry_date", expiryDateEditText.getText().toString());
        editor.putString("certification_credential_id", credentialIdEditText.getText().toString());
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