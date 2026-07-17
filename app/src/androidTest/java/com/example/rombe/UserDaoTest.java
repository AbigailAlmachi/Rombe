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

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private AppDatabase db;
    private UserDao userDao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void insertarYLeerUsuario() {
        // Arrange: crear usuario de prueba
        User user = new User("Abigail", "abigail@correo.com", "123456");
        userDao.insert(user);

        // Act: buscar por correo
        User resultado = userDao.getUserByEmail("abigail@correo.com");

        // Assert: verificar datos
        assertNotNull("El usuario debe existir", resultado);
        assertEquals("El nombre debe coincidir", "Abigail", resultado.name);
        assertEquals("El correo debe coincidir", "abigail@correo.com", resultado.email);
        assertEquals("La contraseña debe coincidir", "123456", resultado.password);
    }

    @Test
    public void loginUsuario() {
        // Arrange: insertar usuario
        User user = new User("Prueba", "test@correo.com", "clave123");
        userDao.insert(user);

        // Act: login correcto
        User loginCorrecto = userDao.login("test@correo.com", "clave123");

        // Assert: credenciales válidas
        assertNotNull("Debe devolver usuario con credenciales correctas", loginCorrecto);
        assertEquals("El nombre debe coincidir", "Prueba", loginCorrecto.name);

        // Act: login incorrecto
        User loginIncorrecto = userDao.login("test@correo.com", "malaclave");

        // Assert: credenciales inválidas
        assertNull("Debe devolver null con credenciales incorrectas", loginIncorrecto);
    }
}
