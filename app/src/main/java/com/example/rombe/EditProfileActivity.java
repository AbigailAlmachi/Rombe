package com.example.rombe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etPassword;
    private Button btnSave;
    private User currentUser;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);
        Button btnBackEditProfile = findViewById(R.id.btnBackEditProfile);

        btnBackEditProfile.setOnClickListener(v -> finish());

        db = AppDatabase.getInstance(this);

        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null) {
            // Buscamos al usuario por nombre (asumiendo que es único según la lógica del plan)
            // En una app real, usaríamos el ID
            currentUser = db.userDao().getUserByName(userName);
            if (currentUser != null) {
                etName.setText(currentUser.name);
                etPassword.setText(currentUser.password);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (currentUser != null) {
                String newName = etName.getText().toString().trim();
                String newPassword = etPassword.getText().toString().trim();

                if (!newName.isEmpty() && !newPassword.isEmpty()) {
                    currentUser.name = newName;
                    currentUser.password = newPassword;
                    db.userDao().update(currentUser);
                    Toast.makeText(this, getString(R.string.profile_updated), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.complete_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
