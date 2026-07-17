package com.example.rombe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // 1. Vincular el RecyclerView del diseño XML
        RecyclerView recyclerView = findViewById(R.id.recyclerRanking);
        
        // 2. Configurar cómo se mostrará la lista (Vertical)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // 3. Obtener base de datos y el nombre del usuario actual
        AppDatabase db = AppDatabase.getInstance(this);
        String currentUserName = getIntent().getStringExtra("USER_NAME");

        // 4. Cargar la lista de puntajes desde Room
        List<Ranking> rankingList = db.rankingDao().getAllOrderedByScore();
        
        // 5. Vincular los datos al adaptador
        if (rankingList != null) {
            RankingAdapter adapter = new RankingAdapter(rankingList, currentUserName);
            recyclerView.setAdapter(adapter);
        }

        // 6. Botón para volver al menú
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
