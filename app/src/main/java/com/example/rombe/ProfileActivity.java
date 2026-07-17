package com.example.rombe;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ProfileActivity extends AppCompatActivity {
    private EditText editName, editEmail;
    private Button btnSave;
    private User currentUser;
    private AppDatabase db;
    private static final String CHANNEL_ID = "profile_updates";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        createNotificationChannel();

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        btnSave = findViewById(R.id.btnSave);
        Button btnBackProfile = findViewById(R.id.btnBackProfile);
        
        btnBackProfile.setOnClickListener(v -> finish());

        // Recuperar el ID del usuario logueado (es más fiable que el nombre si este cambia)
        int userId = getIntent().getIntExtra("USER_ID", -1);
        String userName = getIntent().getStringExtra("USER_NAME");

        db = AppDatabase.getInstance(this);

        new Thread(() -> {
            if (userId != -1) {
                currentUser = db.userDao().getUserById(userId);
            } else if (userName != null) {
                currentUser = db.userDao().getUserByName(userName);
            }

            if (currentUser != null) {
                runOnUiThread(() -> {
                    editName.setText(currentUser.name);
                    editEmail.setText(currentUser.email);
                });
            }
        }).start();

        btnSave.setOnClickListener(v -> {
            if (currentUser != null) {
                String oldName = currentUser.name;
                currentUser.name = editName.getText().toString();
                currentUser.email = editEmail.getText().toString();
                
                new Thread(() -> {
                    db.userDao().update(currentUser);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "¡Cambio correcto!", Toast.LENGTH_SHORT).show();
                        sendNotification();
                        
                        // Actualizar el nombre en el intent para que al volver al menú se vea el cambio
                        getIntent().putExtra("USER_NAME", currentUser.name);
                    });
                }).start();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Cambios de Perfil";
            String description = "Notificaciones sobre cambios en el perfil del usuario";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("¡Cambio Correcto!")
                .setContentText("Tu perfil se ha actualizado a: " + currentUser.name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
