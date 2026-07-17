package com.example.rombe;

import static org.junit.Assert.*;
import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RankingDaoTest {
    private AppDatabase db;
    private RankingDao rankingDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        rankingDao = db.rankingDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertarYListarRankings() {
        Ranking r1 = new Ranking("Jugador1", 100);
        Ranking r2 = new Ranking("Jugador2", 500);
        
        rankingDao.insert(r1);
        rankingDao.insert(r2);

        List<Ranking> lista = rankingDao.getAllOrderedByScore();
        
        assertNotNull(lista);
        assertEquals(2, lista.size());
        // El primero debe ser el de mayor puntaje por el ORDER BY DESC
        assertEquals("Jugador2", lista.get(0).getNombre());
        assertEquals(500, lista.get(0).getPuntaje());
    }
}
