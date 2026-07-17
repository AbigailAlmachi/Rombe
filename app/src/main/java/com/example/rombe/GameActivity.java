package com.example.rombe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa la vista del juego
        gameView = new GameView(this);
        setContentView(gameView);

        // Recibe el nombre y el ID del jugador desde otra Activity
        int userId = getIntent().getIntExtra("USER_ID", -1);
        String playerName = getIntent().getStringExtra("USER_NAME");

        if (userId != -1) {
            gameView.setPlayerId(userId);
        }
        if (playerName != null && !playerName.isEmpty()) {
            gameView.setPlayerName(playerName);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (gameView != null) {
            gameView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gameView != null) {
            gameView.resume();
        }
    }
}
