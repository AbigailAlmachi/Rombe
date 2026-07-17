package com.example.rombe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    // Buscar usuario por nombre
    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    User getUserByName(String name);

    // Buscar usuario por id ✅
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getUserById(int id);

    // Buscar usuario por email ✅
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    // Método para login (nombre o email + contraseña) ✅
    @Query("SELECT * FROM users WHERE (name = :identifier OR email = :identifier) AND password = :password LIMIT 1")
    User login(String identifier, String password);
}
