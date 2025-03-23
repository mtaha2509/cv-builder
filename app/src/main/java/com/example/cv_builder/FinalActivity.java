package com.example.cv_builder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FinalActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("CV Preview");
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);
        TextView summaryTextView = findViewById(R.id.summaryTextView);
        TextView educationTextView = findViewById(R.id.educationTextView);
        TextView experienceTextView = findViewById(R.id.experienceTextView);
        TextView certificationsTextView = findViewById(R.id.certificationsTextView);
        TextView referencesTextView = findViewById(R.id.referencesTextView);
        Button shareButton = findViewById(R.id.shareButton);

        // Load profile image if exists
        try {
            String profileImageUriString = sharedPreferences.getString("profile_image_uri", "");
            if (!profileImageUriString.isEmpty()) {
                Uri profileImageUri = Uri.parse(profileImageUriString);
                profileImageView.setImageURI(profileImageUri);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading profile image", Toast.LENGTH_SHORT).show();
        }

        // Load and display personal details
        String name = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("email", "");
        String phone = sharedPreferences.getString("phone", "");
        String address = sharedPreferences.getString("address", "");

        if (name.isEmpty() && email.isEmpty() && phone.isEmpty() && address.isEmpty()) {
            Toast.makeText(this, "Please fill in your personal details first", Toast.LENGTH_LONG).show();
        }

        nameTextView.setText(name);
        emailTextView.setText(email);
        phoneTextView.setText(phone);
        addressTextView.setText(address);

        // Load and display summary
        summaryTextView.setText(sharedPreferences.getString("summary", ""));

        // Load and format education details
        String education = String.format("%s\n%s in %s\n%s - %s\n%s",
            sharedPreferences.getString("education_institution", ""),
            sharedPreferences.getString("education_degree", ""),
            sharedPreferences.getString("education_field", ""),
            sharedPreferences.getString("education_start_year", ""),
            sharedPreferences.getString("education_end_year", ""),
            sharedPreferences.getString("education_achievements", "")
        );
        educationTextView.setText(education);

        // Load and format experience details
        String experience = String.format("%s\n%s\n%s - %s\n%s",
            sharedPreferences.getString("experience_company", ""),
            sharedPreferences.getString("experience_position", ""),
            sharedPreferences.getString("experience_start_date", ""),
            sharedPreferences.getString("experience_end_date", ""),
            sharedPreferences.getString("experience_responsibilities", "")
        );
        experienceTextView.setText(experience);

        // Load and format certifications
        String certifications = String.format("%s\nIssued by: %s\nIssue Date: %s\nExpiry Date: %s\nCredential ID: %s",
            sharedPreferences.getString("certification_name", ""),
            sharedPreferences.getString("certification_organization", ""),
            sharedPreferences.getString("certification_issue_date", ""),
            sharedPreferences.getString("certification_expiry_date", ""),
            sharedPreferences.getString("certification_credential_id", "")
        );
        certificationsTextView.setText(certifications);

        // Load and format references
        String references = String.format("%s\n%s at %s\nEmail: %s\nPhone: %s\nRelationship: %s",
            sharedPreferences.getString("reference_name", ""),
            sharedPreferences.getString("reference_position", ""),
            sharedPreferences.getString("reference_company", ""),
            sharedPreferences.getString("reference_email", ""),
            sharedPreferences.getString("reference_phone", ""),
            sharedPreferences.getString("reference_relationship", "")
        );
        referencesTextView.setText(references);

        // Set up share button
        shareButton.setOnClickListener(v -> shareCV());
    }

    private void shareCV() {
        try {
            // Create a temporary file to store CV content
            File cvFile = new File(getFilesDir(), "cv.txt");
            FileWriter writer = new FileWriter(cvFile);

            // Write CV content
            writer.append("CURRICULUM VITAE\n\n");
            writer.append("PERSONAL DETAILS\n");
            writer.append("Name: ").append(sharedPreferences.getString("name", "")).append("\n");
            writer.append("Email: ").append(sharedPreferences.getString("email", "")).append("\n");
            writer.append("Phone: ").append(sharedPreferences.getString("phone", "")).append("\n");
            writer.append("Address: ").append(sharedPreferences.getString("address", "")).append("\n\n");

            writer.append("PROFESSIONAL SUMMARY\n");
            writer.append(sharedPreferences.getString("summary", "")).append("\n\n");

            writer.append("EDUCATION\n");
            writer.append("Institution: ").append(sharedPreferences.getString("education_institution", "")).append("\n");
            writer.append("Degree: ").append(sharedPreferences.getString("education_degree", "")).append("\n");
            writer.append("Field: ").append(sharedPreferences.getString("education_field", "")).append("\n");
            writer.append("Duration: ").append(sharedPreferences.getString("education_start_year", ""))
                  .append(" - ").append(sharedPreferences.getString("education_end_year", "")).append("\n");
            writer.append("Achievements: ").append(sharedPreferences.getString("education_achievements", "")).append("\n\n");

            writer.append("WORK EXPERIENCE\n");
            writer.append("Company: ").append(sharedPreferences.getString("experience_company", "")).append("\n");
            writer.append("Position: ").append(sharedPreferences.getString("experience_position", "")).append("\n");
            writer.append("Duration: ").append(sharedPreferences.getString("experience_start_date", ""))
                  .append(" - ").append(sharedPreferences.getString("experience_end_date", "")).append("\n");
            writer.append("Responsibilities: ").append(sharedPreferences.getString("experience_responsibilities", "")).append("\n\n");

            writer.append("CERTIFICATIONS\n");
            writer.append("Name: ").append(sharedPreferences.getString("certification_name", "")).append("\n");
            writer.append("Organization: ").append(sharedPreferences.getString("certification_organization", "")).append("\n");
            writer.append("Issue Date: ").append(sharedPreferences.getString("certification_issue_date", "")).append("\n");
            writer.append("Expiry Date: ").append(sharedPreferences.getString("certification_expiry_date", "")).append("\n");
            writer.append("Credential ID: ").append(sharedPreferences.getString("certification_credential_id", "")).append("\n\n");

            writer.append("REFERENCES\n");
            writer.append("Name: ").append(sharedPreferences.getString("reference_name", "")).append("\n");
            writer.append("Position: ").append(sharedPreferences.getString("reference_position", "")).append("\n");
            writer.append("Company: ").append(sharedPreferences.getString("reference_company", "")).append("\n");
            writer.append("Email: ").append(sharedPreferences.getString("reference_email", "")).append("\n");
            writer.append("Phone: ").append(sharedPreferences.getString("reference_phone", "")).append("\n");
            writer.append("Relationship: ").append(sharedPreferences.getString("reference_relationship", ""));

            writer.close();

            // Get URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(this, 
                "com.example.cv_builder.fileprovider", cvFile);

            // Create share intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share CV via"));

        } catch (IOException e) {
            Toast.makeText(this, "Error sharing CV: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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