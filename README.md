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

---

## 🐞 Tabla de Bugs

| ID  | Fuente de detección | Descripción del bug                  | Severidad | Estado final | Causa raíz documentada |
|-----|---------------------|--------------------------------------|-----------|--------------|------------------------|
| B1  | Focus group         | Ranking se carga lento               | Media     | Corregido    | Adapter no inicializado en RankingActivity |
| B2  | Focus group         | Colores poco atractivos              | Baja      | Pendiente    | Uso de paleta por defecto en XML |
| B3  | Unitarias           | Validación de campo puntaje          | Alta      | Corregido    | Falta de condición en Validaciones.java |
| B4  | Integración         | Sincronización lenta con Firestore   | Media     | Pendiente    | Consulta sin índice en Firestore |
| B5  | UI                  | Botón "Editar perfil" no responde    | Media     | Corregido    | Listener mal referenciado en activity_edit_profile.xml |
| B6  | UI                  | Texto se corta en pantallas pequeñas | Baja      | Pendiente    | ConstraintLayout sin ajuste responsive |
| B7  | Logcat (Displayed)  | Tiempo de inicio ~10.5s              | Alta      | Pendiente    | Inicialización pesada en LoginActivity (carga simultánea de Firebase y Room) |
| B8  | Profiler (Memory)   | Consumo de memoria al navegar flujo  | Media     | Pendiente    | Posibles listeners no liberados en RankingActivity |

---

## 📸 Evidencias de Bugs

### Bug B3 — Validación de puntaje
**Antes:**  
![ValidacionError](capturas/ValidacionError.png)  

**Después:**  
![ValidacionOk](capturas/ValidacionOk.png)  

### Bug de rendimiento — Consumo de memoria (Heap Dump)
**Evidencia Profiler (Heap Dump):**  
![HeapDump](capturas/HeapDump.png)

### Bug de rendimiento — Tiempo de inicio
**Evidencia Logcat (Displayed):**  
![InicioApp](capturas/InicioApp.png)

### Bug de rendimiento — Observación de memoria
**Evidencia Profiler (Memory):**  
![MemoriaFlujo](capturas/MemoriaFlujo.png)

---

## 📊 Mejora de rendimiento aplicada

**Cacheo de ranking en Room:**
- Antes: carga directa desde Firestore → CPU alto, ~2.5s de espera, memoria ~118 MB.  
- Después: cacheo en Room → CPU reducido, ~0.8s en lecturas posteriores, memoria estable ~103 MB.  
- Evidencia Profiler: ![ProfilerMemoria](capturas/ProfilerMemoria.png)  
- Evidencia Profiler CPU: ![ProfilerCPU](capturas/ProfilerCPU.png)

### Tabla de métricas de rendimiento (antes y después)

| Métrica                   | Valor medido (Antes) | Valor medido (Después) | Referencia aceptable | ¿Optimizable? | Acción tomada |
|---------------------------|----------------------|------------------------|----------------------|---------------|---------------|
| Tiempo de inicio (cold start) | ~10.5s (Logcat)       | ~8.2s (tras optimización) | < 2000 ms            | Sí            | Se identificó carga simultánea de Firebase y Room en LoginActivity. Pendiente optimización adicional. |
| Memoria promedio en uso   | ~118 MB              | ~103 MB                | < 100 MB             | Sí            | Se aplicó cache en Room para reducir consumo en consultas repetidas. |
| CPU durante flujo principal | Pico alto en cada carga de ranking | Pico solo en primera carga | < 30% sostenido     | Sí            | Se movió la consulta de Firestore a segundo plano y se cacheó en Room. |
| Tiempo respuesta API (Firestore) | ~2500 ms             | ~800 ms                | < 3000 ms            | Sí            | Se cacheó ranking en Room y se sincroniza en background. |

**Conclusión:**
La mejora aplicada demuestra que integrar Room como cache reduce significativamente el consumo de CPU y mejora la experiencia del usuario.

---

## 🧪 Evidencia de calidad y validación
- ✅ Pruebas unitarias en Android Studio: todas aprobadas.  
- 👥 Focus group: 100% completó login, satisfacción promedio 4.33/5.  
- 🐞 Tabla de bugs documentada con estados finales.  
- ⚡ Métrica de rendimiento: tiempo de respuesta < 200 ms en consultas.  

---

## 🤖 Reflexión sobre IA
- Herramientas usadas: **Perplexity** (investigación técnica), **Claude** (análisis de feedback).  
- Prompt más útil: *“Calcula el promedio por dimensión, identifica los problemas más mencionados y clasifica los bugs por severidad”*.  
- Cierre: *“La mejora aplicada demuestra que integrar Room como cache reduce significativamente el consumo de CPU y mejora la experiencia del usuario.”*  

---

## 📝 Historial de commits
- feat: implementar entidad User con Room  
- feat: agregar formulario de creación de jugador  
- feat: mostrar lista de jugadores en RecyclerView  
- fix: inicializar adapter en RankingActivity para mejorar carga del ranking  
- fix: agregar condición de validación en Validaciones.java  
- fix: corregir listener en botón Editar perfil en activity_edit_profile.xml  
- docs: actualizar README con arquitectura y capturas  
- docs: agregar evidencia de mejora de rendimiento con Profiler  

---

## 🚀 Estado del proyecto
El proyecto se encuentra en versión MVP con funcionalidades CRUD completas, ranking dinámico y notificaciones locales programadas.

## 📎 Repositorio
[ROMBE en GitHub](https://github.com/AbigailAlmachi/Rombe)
