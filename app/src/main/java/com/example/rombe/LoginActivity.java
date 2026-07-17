package com.example.rombe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPassword;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView linkRegistro = findViewById(R.id.linkRegistro);

        // Inicializar Base de Datos
        db = AppDatabase.getInstance(this);

        // Configurar botón Iniciar Sesión
        btnLogin.setOnClickListener(v -> {
            String identifier = edtUser.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (identifier.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Login en hilo secundario para evitar bloqueos
            new Thread(() -> {
                User user = db.userDao().login(identifier, password);

                runOnUiThread(() -> {
                    if (user != null) {
                        Toast.makeText(LoginActivity.this, "¡Bienvenido " + user.name + "!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("USER_ID", user.id);
                        intent.putExtra("USER_NAME", user.name);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        // Configurar enlace de registro
        linkRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
