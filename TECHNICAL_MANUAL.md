# Manual Técnico — ROMBE v1.0

## 1. Descripción del sistema
**Problema que resuelve:** Facilita la gestión de jugadores en un juego tipo puzzle/Tamagotchi, con login, perfil editable y ranking dinámico.  
**Usuario objetivo:** Estudiantes y jugadores casuales que buscan un prototipo funcional de videojuego Android con persistencia de datos.  
**Alcance del MVP:** Login/registro, CRUD de jugadores, ranking dinámico, perfil editable, notificaciones locales, cache en Room, evidencias de rendimiento.

---

## 2. Arquitectura de la aplicación
**Diagrama de capas:**
- **UI:** Activities (`LoginActivity`, `RegisterActivity`, `MainActivity`, `ProfileActivity`, `RankingActivity`) + RecyclerView.  
- **Lógica:** Repositorios (`RankingRepository`), ViewModels (MVVM).  
- **Datos:** Room (`AppDatabase`, `UserDao`), Firebase Firestore.  

**Patrón de diseño usado:** MVVM + Repository.  
**Notas:** Se aplicó el patrón State en el módulo Tamagotchi.

---

## 3. Modelo de datos
**Entidades principales:**
- `User`: id, nombre, email, password, puntaje.  
- `Ranking`: lista de usuarios ordenados por puntaje.  

**Relaciones:**
- `User` es la entidad principal.  
- `Ranking` se deriva de la colección de usuarios ordenados.  

**Claves:**
- `id` como clave primaria en Room.  
- `email` como clave única para login.  

---

## 4. Tecnologías y librerías
- **Framework:** Android Studio (Java/Kotlin).  
- **Base de datos:** Room (AndroidX), Firebase Firestore.  
- **Librerías:**  
  - `androidx.lifecycle:livedata`  
  - `androidx.recyclerview:recyclerview`  
  - `com.google.firebase:firebase-firestore`  
  - `androidx.room:room-runtime`  
  - `androidx.room:room-compiler`  
  - `androidx.core:core-ktx`  
  - `androidx.appcompat:appcompat`  

---

## 5. Instrucciones para compilar
**Requisitos:**
- Android Studio Giraffe o superior.  
- JDK 17.  
- SDK mínimo: 24 (Android 7.0).  

**Pasos:**
1. Clonar el repositorio:  
   ```bash
   git clone https://github.com/AbigailAlmachi/ROMBE.git
