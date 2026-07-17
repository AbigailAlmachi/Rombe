package com.example.rombe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying;
    private final Paint paint;
    private final SurfaceHolder holder;

    // Pelota
    private int ballX, ballY, ballRadius, ballSpeedX, ballSpeedY;

    // Paleta
    private int paddleX, paddleY, paddleWidth, paddleHeight;

    // Bloques
    private final ArrayList<Rect> blocks = new ArrayList<>();
    private final List<Brick> levelBricks = new ArrayList<>();

    // Puntaje y nivel
    private int score = 0;
    private int level = 1;
    private boolean isGameOver = false;
    private boolean isLevelComplete = false;
    private boolean firstTime = true;
    private String playerName = "Jugador";
    private int userId = -1;

    // Combo y Logros
    private int combo = 0;
    private String achievementText = "";
    private int achievementTimer = 0;

    // Sonidos
    private SoundPool soundPool;
    private int soundHit, soundGameOver;

    // Botón pausa y menú
    private final Rect pauseButton = new Rect();
    private boolean isPausedMenuVisible = false;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        paint = new Paint();
        initSounds();
    }

    private void initSounds() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        // Usamos getIdentifier para evitar errores si los archivos no existen en res/raw
        int resHit = getContext().getResources().getIdentifier("hit", "raw", getContext().getPackageName());
        int resGameOver = getContext().getResources().getIdentifier("game_over", "raw", getContext().getPackageName());

        if (resHit != 0) {
            soundHit = soundPool.load(getContext(), resHit, 1);
        } else {
            soundHit = -1;
        }
        if (resGameOver != 0) {
            soundGameOver = soundPool.load(getContext(), resGameOver, 1);
        } else {
            soundGameOver = -1;
        }
    }

    public void setPlayerName(String name) {
        if (name != null && !name.isEmpty()) {
            this.playerName = name;
        }
    }
    public void setPlayerId(int id) {
        this.userId = id;
    }

    private void initPositions() {
        int screenWidth = getWidth();
        int screenHeight = getHeight();

        paddleWidth = screenWidth / 3;
        paddleHeight = 40;
        paddleX = (screenWidth - paddleWidth) / 2;
        paddleY = screenHeight - 200;

        ballRadius = 25;
        resetBall();

        createBlocks();

        pauseButton.set(getWidth() - 200, 180, getWidth() - 50, 280);

        firstTime = false;
    }

    private void resetBall() {
        ballX = getWidth() / 2;
        ballY = paddleY - ballRadius - 10;

        int baseSpeed = 18;
        int incremento = level * 2;
        ballSpeedX = baseSpeed + incremento;
        ballSpeedY = -(baseSpeed + incremento);
    }

    private void createBlocks() {
        blocks.clear();
        levelBricks.clear();

        int screenWidth = getWidth();
        final int blockWidth = (screenWidth - 200) / 6;
        final int blockHeight = 60;
        final int padding = 15;
        final int startTop = getHeight() / 6;

        if (level == 1) {
            // Nivel 1: Rectángulo simple, 3 filas. Fáciles (1 golpe).
            for (int fila = 0; fila < 3; fila++) {
                int x = 100;
                int y = startTop + fila * (blockHeight + padding);
                for (int i = 0; i < 6; i++) {
                    blocks.add(new Rect(x, y, x + blockWidth, y + blockHeight));
                    levelBricks.add(new Brick("Azul", 100, 1));
                    x += blockWidth + padding;
                }
            }
        } else if (level == 2) {
            // Nivel 2: Introducción de bloques indestructibles (Piedra)
            for (int fila = 0; fila < 4; fila++) {
                int x = 100;
                int y = startTop + fila * (blockHeight + padding);
                for (int i = 0; i < 6; i++) {
                    blocks.add(new Rect(x, y, x + blockWidth, y + blockHeight));
                    // Algunos son de piedra (indestructibles)
                    if ((fila == 1 || fila == 2) && (i == 2 || i == 3)) {
                        levelBricks.add(new Brick("Gris", 0, 1, true));
                    } else {
                        levelBricks.add(new Brick("Naranja", 150, 1));
                    }
                    x += blockWidth + padding;
                }
            }
        } else if (level == 3) {
            // Nivel 3: Bloques que resisten 2 golpes + Obstáculos
            for (int fila = 0; fila < 5; fila++) {
                int x = 100;
                int y = startTop + fila * (blockHeight + padding);
                for (int i = 0; i < 6; i++) {
                    blocks.add(new Rect(x, y, x + blockWidth, y + blockHeight));
                    if (i == 0 || i == 5) {
                        levelBricks.add(new Brick("Gris", 0, 1, true)); // Lados de piedra
                    } else {
                        levelBricks.add(new Brick("Verde", 200, 2)); // 2 golpes
                    }
                    x += blockWidth + padding;
                }
            }
        } else if (level == 4) {
            // Nivel 4: Resistencia de 3 golpes y más velocidad
            for (int fila = 0; fila < 5; fila++) {
                int x = 100;
                int y = startTop + fila * (blockHeight + padding);
                for (int i = 0; i < 6; i++) {
                    blocks.add(new Rect(x, y, x + blockWidth, y + blockHeight));
                    if ((fila + i) % 3 == 0) {
                        levelBricks.add(new Brick("Rojo", 300, 3)); // 3 golpes
                    } else if ((fila + i) % 3 == 1) {
                        levelBricks.add(new Brick("Gris", 0, 1, true));
                    } else {
                        levelBricks.add(new Brick("Naranja", 150, 1));
                    }
                    x += blockWidth + padding;
                }
            }
        } else {
            // Nivel 5: El gran reto. Mezcla total y mucha resistencia.
            for (int fila = 0; fila < 6; fila++) {
                int x = 100;
                int y = startTop + fila * (blockHeight + padding);
                for (int i = 0; i < 6; i++) {
                    blocks.add(new Rect(x, y, x + blockWidth, y + blockHeight));
                    if (fila % 2 == 0) {
                        levelBricks.add(new Brick("Rojo", 500, 3));
                    } else {
                        if (i % 2 == 0) levelBricks.add(new Brick("Gris", 0, 1, true));
                        else levelBricks.add(new Brick("Verde", 400, 2));
                    }
                    x += blockWidth + padding;
                }
            }
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            if (firstTime && getWidth() > 0) {
                initPositions();
            }

            if (!isGameOver && !isLevelComplete && !firstTime && !isPausedMenuVisible) {
                update();
            }
            draw();
            control();
        }
    }

    private void update() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX - ballRadius <= 0 || ballX + ballRadius >= getWidth()) {
            ballSpeedX *= -1;
        }

        if (ballY - ballRadius <= 0) {
            ballSpeedY *= -1;
        }

        if (ballY + ballRadius >= paddleY && ballY + ballRadius <= paddleY + paddleHeight) {
            if (ballX >= paddleX && ballX <= paddleX + paddleWidth) {
                ballSpeedY *= -1;
                ballY = paddleY - ballRadius; // asegura que no se quede pegada
                soundPool.play(soundHit, 1, 1, 0, 0, 1);
                combo = 0; // Reiniciar combo al tocar la paleta
            }
        }

        for (int i = 0; i < blocks.size(); i++) {
                final Rect ballRect = new Rect(ballX - ballRadius, ballY - ballRadius,
                        ballX + ballRadius, ballY + ballRadius);

                if (Rect.intersects(blocks.get(i), ballRect)) {
                    final Brick brick = levelBricks.get(i);
                
                if (!brick.isIndestructible) {
                    brick.resistance--;
                    soundPool.play(soundHit, 1, 1, 0, 0, 1);

                    if (brick.resistance <= 0) {
                        combo++;
                        int pointsEarned = brick.points;
                        
                        // Lógica de Bonificación Doble (Nivel 1 y general)
                        if (combo >= 2) {
                            pointsEarned *= 2;
                            achievementText = "¡DOBLE PUNTUACIÓN!";
                            achievementTimer = 60; // Mostrar durante ~1 segundo
                        }
                        
                        score += pointsEarned;
                        blocks.remove(i);
                        levelBricks.remove(i);
                    }
                } else {
                    // Si es piedra, solo rebota
                    soundPool.play(soundHit, 0.5f, 0.5f, 0, 0, 0.8f);
                }

                ballSpeedY *= -1;
                break; 
            }
        }
        
        if (achievementTimer > 0) achievementTimer--;

        // ✅ Nivel completado
        if (isAllDestructibleGone() && !isLevelComplete) {
            isLevelComplete = true;
            if (level == 5) {
                achievementText = "¡CAMPEÓN DE ROMBE!";
                achievementTimer = 120;
                saveScore(); // Guardar al ganar el último nivel
            }
        }

        // ✅ Game Over: Ahora guarda el puntaje automáticamente
        if (ballY > getHeight()) {
            isGameOver = true;
            soundPool.play(soundGameOver, 1, 1, 0, 0, 1);
            saveScore(); // 👈 Guarda el nombre real y el puntaje
            // No cambiamos isPlaying a false para que el bucle run() siga detectando toques
        }
    }


    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(30, 30, 45));

            // Paleta
            paint.setColor(Color.WHITE);
            canvas.drawRect(paddleX, paddleY, paddleX + paddleWidth, paddleY + paddleHeight, paint);

            // Pelota
            paint.setColor(Color.RED);
            canvas.drawCircle(ballX, ballY, ballRadius, paint);

            // Bloques
            for (int i = 0; i < blocks.size(); i++) {
                Brick brick = levelBricks.get(i);
                if (brick.isIndestructible) {
                    paint.setColor(Color.GRAY);
                } else {
                    switch (brick.resistance) {
                        case 3: paint.setColor(Color.RED); break;
                        case 2: paint.setColor(Color.GREEN); break;
                        default: 
                            if (brick.color.equals("Naranja")) paint.setColor(Color.rgb(255, 165, 0));
                            else paint.setColor(Color.CYAN); 
                            break;
                    }
                }
                canvas.drawRect(blocks.get(i), paint);
                
                // Borde del bloque
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(2);
                canvas.drawRect(blocks.get(i), paint);
                paint.setStyle(Paint.Style.FILL);
            }

            // Puntaje y nivel
            paint.setColor(Color.YELLOW);
            paint.setTextSize(60);
            canvas.drawText("Puntos: " + score, 50, 350, paint);
            canvas.drawText("Nivel: " + level, getWidth() - 300, 350, paint);
            
            // Mostrar Logro si existe
            if (achievementTimer > 0) {
                paint.setColor(Color.rgb(255, 215, 0)); // Dorado
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(achievementText, getWidth() / 2f, 450, paint);
                paint.setTextAlign(Paint.Align.LEFT);
            }

            // Botón de pausa
            paint.setColor(Color.LTGRAY);
            canvas.drawRect(pauseButton, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("II", (float)pauseButton.centerX(), (float)pauseButton.centerY() + 15, paint);
            paint.setTextAlign(Paint.Align.LEFT);

            // Menú de pausa
            if (isPausedMenuVisible) {
                paint.setColor(Color.argb(200, 0, 0, 0));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

                paint.setColor(Color.WHITE);
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("PAUSA", getWidth() / 2f, getHeight() / 2f - 200, paint);

                final Rect continuarBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 - 100,
                        getWidth() / 2 + 200, getHeight() / 2);
                final Rect reiniciarBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 + 50,
                        getWidth() / 2 + 200, getHeight() / 2 + 150);
                final Rect salirBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 + 200,
                        getWidth() / 2 + 200, getHeight() / 2 + 300);

                paint.setColor(Color.GREEN);
                canvas.drawRect(continuarBtn, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText("CONTINUAR", getWidth() / 2f, getHeight() / 2f - 40, paint);

                paint.setColor(Color.YELLOW);
                canvas.drawRect(reiniciarBtn, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText("REINICIAR", getWidth() / 2f, getHeight() / 2f + 110, paint);

                paint.setColor(Color.RED);
                canvas.drawRect(salirBtn, paint);
                paint.setColor(Color.BLACK);
                canvas.drawText("SALIR", getWidth() / 2f, getHeight() / 2f + 260, paint);

                paint.setTextAlign(Paint.Align.LEFT);
            }

            // ✅ Mensaje de nivel completado
            if (isLevelComplete) {
                paint.setColor(Color.argb(220, 0, 0, 0));
                canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

                paint.setColor(Color.GREEN);
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("¡Nivel completado!", getWidth() / 2f, getHeight() / 2f - 50, paint);

                paint.setTextSize(60);
                canvas.drawText("Toca para continuar", getWidth() / 2f, getHeight() / 2f + 50, paint);
                paint.setTextAlign(Paint.Align.LEFT);
            }

            // Game Over
            if (isGameOver) {
                paint.setColor(Color.RED);
                paint.setTextSize(100);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("GAME OVER!", getWidth() / 2f, getHeight() / 2f, paint);

                paint.setTextSize(60);
                canvas.drawText("Toca para reiniciar", getWidth() / 2f, getHeight() / 2f + 100, paint);
                paint.setTextAlign(Paint.Align.LEFT);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }


    private void control() {
        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!isGameOver && !isLevelComplete && isPlaying && !isPausedMenuVisible) {
                paddleX = (int) event.getX() - paddleWidth / 2;
                if (paddleX < 0) paddleX = 0;
                if (paddleX + paddleWidth > getWidth()) paddleX = getWidth() - paddleWidth;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Botón pausa
            if (pauseButton.contains((int) event.getX(), (int) event.getY())) {
                // Mostrar/ocultar menú de pausa sin detener el hilo
                isPausedMenuVisible = !isPausedMenuVisible;
                return true;
            }

            // Menú de pausa
            if (isPausedMenuVisible) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                final Rect continuarBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 - 100,
                        getWidth() / 2 + 200, getHeight() / 2);
                final Rect reiniciarBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 + 50,
                        getWidth() / 2 + 200, getHeight() / 2 + 150);
                final Rect salirBtn = new Rect(getWidth() / 2 - 200, getHeight() / 2 + 200,
                        getWidth() / 2 + 200, getHeight() / 2 + 300);

                if (continuarBtn.contains(x, y)) {
                    isPausedMenuVisible = false;
                    isPlaying = true;
                } else if (reiniciarBtn.contains(x, y)) {
                    isPausedMenuVisible = false;
                    score = 0;
                    initPositions();
                    isPlaying = true;
                } else if (salirBtn.contains(x, y)) {
                    System.exit(0);
                }
                return true;
            }

            // Game Over
            if (isGameOver) {
                score = 0;
                level = 1;
                isGameOver = false;
                initPositions();
            }
            // Nivel completado
            else if (isLevelComplete) {
                if (level < 5) {
                    level++;
                    isLevelComplete = false;
                    initPositions();
                } else {
                    // Si ganó el nivel 5, volver al menú o algo similar
                    isPlaying = false;
                    ((android.app.Activity)getContext()).finish();
                }
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void pause() {
        isPlaying = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void saveScore() {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            // Si tenemos el userId, buscamos el nombre actual para asegurar que guardamos el más reciente
            String nameToSave = playerName;
            if (userId != -1) {
                User user = db.userDao().getUserById(userId);
                if (user != null) {
                    nameToSave = user.name;
                }
            }
            db.rankingDao().insert(new Ranking(nameToSave, score));
        }).start();
    }

    private boolean isAllDestructibleGone() {
        for (Brick b : levelBricks) {
            if (!b.isIndestructible) return false;
        }
        return true;
    }
}
