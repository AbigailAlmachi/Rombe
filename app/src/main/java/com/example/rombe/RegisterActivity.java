package com.example.rombe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword;
    private Button btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        Button btnBackRegister = findViewById(R.id.btnBackRegister);

        db = AppDatabase.getInstance(this);

        btnBackRegister.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                // Verificar si ya existe el usuario o email
                User existingUser = db.userDao().getUserByName(name);
                User existingEmail = db.userDao().getUserByEmail(email);

                if (existingUser != null || existingEmail != null) {
                    runOnUiThread(() -> Toast.makeText(this, "El usuario o email ya existen", Toast.LENGTH_SHORT).show());
                    return;
                }

                User user = new User(name, email, password);
                db.userDao().insert(user);
                
                runOnUiThread(() -> {
                    Toast.makeText(this, "¡Cuenta creada! Ahora inicia sesión", Toast.LENGTH_SHORT).show();
                    finish(); // Regresa al Login
                });
            }).start();
        });
    }
}
