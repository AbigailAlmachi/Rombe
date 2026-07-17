package com.example.rombe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private String userName;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperar datos del usuario logueado
        userId = getIntent().getIntExtra("USER_ID", -1);
        userName = getIntent().getStringExtra("USER_NAME");
        if (userName == null) userName = "Jugador";

        tvWelcome = findViewById(R.id.tvWelcome);

        // Botón Jugar
        Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
        });

        // Botón Ranking
        Button btnRanking = findViewById(R.id.btnRanking);
        btnRanking.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RankingActivity.class);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
        });


        // Botón Perfil
        Button btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("USER_NAME", userName);
            startActivity(intent);
        });

        // Botón Cerrar Sesión
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar el nombre cada vez que volvemos a la pantalla principal
        if (userId != -1) {
            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                User user = db.userDao().getUserById(userId);
                if (user != null) {
                    userName = user.name;
                    runOnUiThread(() -> tvWelcome.setText("¡Bienvenid@, " + userName + "!"));
                }
            }).start();
        } else {
            tvWelcome.setText("¡Bienvenid@, " + userName + "!");
        }
    }
}
