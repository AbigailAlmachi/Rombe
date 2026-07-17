package com.example.rombe;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();
        // Asegurarse de que el usuario de prueba exista en la DB de forma sincrónica
        activityRule.getScenario().onActivity(activity -> {
            AppDatabase db = AppDatabase.getInstance(activity);
            // Ejecutamos directamente ya que allowMainThreadQueries está habilitado en AppDatabase
            User existing = db.userDao().getUserByEmail("abigail@correo.com");
            if (existing == null) {
                User testUser = new User("Abigail", "abigail@correo.com", "123456");
                db.userDao().insert(testUser);
            }
        });
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void loginConCredencialesCorrectas() {
        onView(withId(R.id.edtUser))
                .perform(typeText("abigail@correo.com"), closeSoftKeyboard());
        onView(withId(R.id.edtPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        // Verificar que se inicia MainActivity con el nombre correcto
        intended(allOf(
                hasComponent(MainActivity.class.getName()),
                hasExtra("USER_NAME", "Abigail")
        ));
    }

    @Test
    public void loginConCredencialesIncorrectas() {
        onView(withId(R.id.edtUser))
                .perform(typeText("error@correo.com"), closeSoftKeyboard());
        onView(withId(R.id.edtPassword))
                .perform(typeText("000000"), closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());

        // Seguimos en LoginActivity si las credenciales son incorrectas
    }

    @Test
    public void navegarARegistro() {
        onView(withId(R.id.linkRegistro)).perform(click());
        intended(hasComponent(RegisterActivity.class.getName()));
    }
}
