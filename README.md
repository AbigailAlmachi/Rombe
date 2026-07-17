# 🟠 ROMBE — Rompe Bloques

## 📱 Descripción del problema que resuelve
Muchos juegos móviles interrumpen la experiencia con anuncios o pantallas innecesarias, generando frustración y pérdida de tiempo.  
ROMBE elimina esas barreras: no tiene anuncios, inicia de forma directa y ofrece mecánicas simples que garantizan entretenimiento rápido.

## 🎯 Objetivo de la aplicación
Desarrollar un juego ligero que permita iniciar partidas rápidas, romper bloques y acumular puntuaciones, fomentando la competencia amistosa y el entretenimiento casual.

## ✅ Historias de usuario del MVP
1. Como jugador quiero iniciar sesión para guardar mi progreso.  
2. Como jugador quiero empezar una partida rompiendo bloques.  
3. Como jugador quiero ver mi puntaje al terminar la partida.  
4. Como jugador quiero aparecer en un ranking para competir con otros.  
5. Como jugador quiero editar mi perfil para personalizar mi experiencia.  

## 🧰 Tecnología usada
- Android Studio  
- Lenguaje: Java  
- Gradle para la gestión de dependencias  
- Firebase Firestore para sincronización de datos  
- Room (SQLite) para almacenamiento local  
- Navigation Component para la navegación entre pantallas  

## 🏗️ Arquitectura
- MVVM (Modelo, Vista, ViewModel)  
- Room (SQLite) para almacenamiento local  
- Firebase Authentication y Firestore para login y datos  
- Navigation Component para flujo entre pantallas  

## ⚙️ Funcionalidades implementadas
- Login y registro de usuario  
- Validaciones de campos  
- CRUD completo de jugadores (crear, leer, actualizar, eliminar)  
- Ranking dinámico con RecyclerView  
- Perfil editable  
- Notificaciones locales  

## 📸 Capturas de la aplicación
![Lista de jugadores](capturas/ListadeJugadores.png)  
![Crear jugador](capturas/CrearJugador.png)  
![Editar jugador](capturas/EditarJugador.png)  

## 🧪 Evidencia de calidad y validación
- ✅ Pruebas unitarias en Android Studio: todas aprobadas.  
- 👥 Focus group: 100% completó login, satisfacción promedio 4.33/5.  
- 🐞 Tabla de bugs: Alta: 0 | Media: 1 (ranking lento) | Baja: 2 (colores).  
- ⚡ Métrica de rendimiento: tiempo de respuesta < 200 ms en consultas.  

## 🤖 Reflexión sobre IA
- Herramientas usadas: **Perplexity** (investigación técnica), **Claude** (análisis de feedback).  
- Prompt más útil: *“Calcula el promedio por dimensión, identifica los problemas más mencionados y clasifica los bugs por severidad”*.  
  → Resultado: priorizamos qué bugs arreglar primero antes de la entrega, enfocándonos en los críticos y dejando los detalles visuales para después.  
- Caso de error: Claude clasificó como bug un cambio de color que era solo estético.  
- Cierre: *“Si empezáramos de nuevo, integraríamos IA desde la fase de diseño para acelerar aún más el desarrollo y evitar errores de interpretación.”*  

## 📝 Historial de commits
- feat: implementar entidad Jugador con Room  
- feat: agregar formulario de creación de jugador  
- feat: mostrar lista de jugadores en RecyclerView  
- fix: corregir validación de campo puntaje  
- docs: actualizar README con arquitectura y capturas  

## 🚀 Estado del proyecto
El proyecto se encuentra en versión MVP con funcionalidades CRUD completas, ranking dinámico y notificaciones locales programadas.

## 📎 Repositorio
[ROMBE en GitHub](https://github.com/AbigailAlmachi/Rombe)
