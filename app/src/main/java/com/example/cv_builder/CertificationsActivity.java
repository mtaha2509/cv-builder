package com.example.cv_builder;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
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
        sharedPreferences = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Load saved data
        loadSavedData();

        // Set up button click listeners
        saveButton.setOnClickListener(v -> validateAndSaveData());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loadSavedData() {
        certificationNameEditText.setText(sharedPreferences.getString("certification_name", ""));
        issuingOrganizationEditText.setText(sharedPreferences.getString("certification_organization", ""));
        issueDateEditText.setText(sharedPreferences.getString("certification_issue_date", ""));
        expiryDateEditText.setText(sharedPreferences.getString("certification_expiry_date", ""));
        credentialIdEditText.setText(sharedPreferences.getString("certification_credential_id", ""));
    }

    private void validateAndSaveData() {
        String certificationName = certificationNameEditText.getText().toString().trim();
        String organization = issuingOrganizationEditText.getText().toString().trim();
        String issueDate = issueDateEditText.getText().toString().trim();
        String expiryDate = expiryDateEditText.getText().toString().trim();
        String credentialId = credentialIdEditText.getText().toString().trim();

        // Validate required fields
        if (TextUtils.isEmpty(certificationName)) {
            certificationNameEditText.setError("Certification name is required");
            certificationNameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(organization)) {
            issuingOrganizationEditText.setError("Issuing organization is required");
            issuingOrganizationEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(issueDate)) {
            issueDateEditText.setError("Issue date is required");
            issueDateEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(expiryDate)) {
            expiryDateEditText.setError("Expiry date is required");
            expiryDateEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(credentialId)) {
            credentialIdEditText.setError("Credential ID is required");
            credentialIdEditText.requestFocus();
            return;
        }

        // Save data if validation passes
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("certification_name", certificationName);
        editor.putString("certification_organization", organization);
        editor.putString("certification_issue_date", issueDate);
        editor.putString("certification_expiry_date", expiryDate);
        editor.putString("certification_credential_id", credentialId);
        editor.apply();

        Toast.makeText(this, "Certification details saved successfully", Toast.LENGTH_SHORT).show();
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