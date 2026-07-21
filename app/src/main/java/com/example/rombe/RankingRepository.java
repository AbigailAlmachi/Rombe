package com.example.rombe;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RankingRepository {
    private final JugadorDao jugadorDao;
    private final FirebaseFirestore firestore;
    private final Executor executor;

    public RankingRepository(JugadorDao jugadorDao) {
        this.jugadorDao = jugadorDao;
        this.firestore = FirebaseFirestore.getInstance();
        this.executor = Executors.newSingleThreadExecutor();
    }

    // Método principal: obtiene ranking cacheado y lo actualiza desde Firestore
    public LiveData<List<Jugador>> getRanking() {
        // Primero devuelve lo que haya en Room
        LiveData<List<Jugador>> localRanking = jugadorDao.getAllRanking();

        // Luego actualiza desde Firestore en background
        firestore.collection("jugadores")
                .orderBy("puntaje", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Jugador> jugadores = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot) {
                        Jugador jugador = doc.toObject(Jugador.class);
                        if (jugador != null) {
                            jugadores.add(jugador);
                        }
                    }
                    // Guardar en Room para cachear
                    executor.execute(() -> jugadorDao.insertAll(jugadores));
                });

        // IMPORTANTE: devolver el LiveData
        return localRanking;
    }
}
