package com.example.cv_builder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView profileImageView;
    private Uri selectedImageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("CVBuilderPrefs", MODE_PRIVATE);

        // Initialize UI elements
        profileImageView = findViewById(R.id.profileImageView);
        CardView personalDetailsCard = findViewById(R.id.personalDetailsCard);
        CardView summaryCard = findViewById(R.id.summaryCard);
        CardView educationCard = findViewById(R.id.educationCard);
        CardView experienceCard = findViewById(R.id.experienceCard);
        CardView certificationsCard = findViewById(R.id.certificationsCard);
        CardView referencesCard = findViewById(R.id.referencesCard);
        Button previewCVBtn = findViewById(R.id.previewButton);

        // Load saved profile image
        String savedImageUri = sharedPreferences.getString("profile_image_uri", "");
        if (!savedImageUri.isEmpty()) {
            profileImageView.setImageURI(Uri.parse(savedImageUri));
        }

        // Set click listeners
        profileImageView.setOnClickListener(this);
        personalDetailsCard.setOnClickListener(this);
        summaryCard.setOnClickListener(this);
        educationCard.setOnClickListener(this);
        experienceCard.setOnClickListener(this);
        certificationsCard.setOnClickListener(this);
        referencesCard.setOnClickListener(this);
        previewCVBtn.setOnClickListener(this);

        // Initialize image picker launcher
        imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    profileImageView.setImageURI(selectedImageUri);
                    // Save the image URI
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile_image_uri", selectedImageUri.toString());
                    editor.apply();
                }
            }
        );
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int viewId = v.getId();

        if (viewId == R.id.profileImageView) {
            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(pickImage);
            return;
        }

        if (viewId == R.id.personalDetailsCard) {
            intent = new Intent(this, PersonalDetailsActivity.class);
        } else if (viewId == R.id.summaryCard) {
            intent = new Intent(this, SummaryActivity.class);
        } else if (viewId == R.id.educationCard) {
            intent = new Intent(this, EducationActivity.class);
        } else if (viewId == R.id.experienceCard) {
            intent = new Intent(this, ExperienceActivity.class);
        } else if (viewId == R.id.certificationsCard) {
            intent = new Intent(this, CertificationsActivity.class);
        } else if (viewId == R.id.referencesCard) {
            intent = new Intent(this, ReferencesActivity.class);
        } else if (viewId == R.id.previewButton) {
            intent = new Intent(this, FinalActivity.class);
        } else {
            return;
        }

        startActivity(intent);
    }
}