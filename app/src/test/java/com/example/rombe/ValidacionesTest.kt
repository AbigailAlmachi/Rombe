package com.example.rombe

import org.junit.Assert
import org.junit.Test

class ValidacionesTest {
    @Test
    fun validarEmail_correctoYconCasoDeBorde() {
        // Arrange
        val emailValido = "usuario@dominio.com"
        val emailInvalido = "usuario@@dominio.com"

        // Act
        val resultadoValido = Validaciones.validarEmail(emailValido)
        val resultadoInvalido = Validaciones.validarEmail(emailInvalido)

        // Assert
        Assert.assertTrue("El correo válido debe pasar", resultadoValido)
        Assert.assertFalse("El correo con dos @ debe fallar", resultadoInvalido)
    }

    @Test
    fun validarPassword_seguraYcorta() {
        // Arrange
        val passwordValida = "abc12345"
        val passwordInvalida = "123"

        // Act
        val resultadoValida = Validaciones.validarPassword(passwordValida)
        val resultadoInvalida = Validaciones.validarPassword(passwordInvalida)

        // Assert
        Assert.assertTrue("La contraseña larga debe ser aceptada", resultadoValida)
        Assert.assertFalse("La contraseña corta debe ser rechazada", resultadoInvalida)
    }

    @Test
    fun calcularPuntaje_conComboYsinCombo() {
        // Arrange
        val base = 100
        val comboNormal = 2
        val comboBorde = 3

        // Act
        val puntajeNormal = Validaciones.calcularPuntaje(base, comboNormal)
        val puntajeCombo = Validaciones.calcularPuntaje(base, comboBorde)

        // Assert
        Assert.assertEquals("Con combo normal debe sumar 10", 110, puntajeNormal.toLong())
        Assert.assertEquals("Con combo múltiplo de 3 debe sumar 20", 120, puntajeCombo.toLong())
    }
}