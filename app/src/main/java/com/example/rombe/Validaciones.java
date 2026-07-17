package com.example.rombe;

public class Validaciones {

    public static boolean validarEmail(String email) {
        // Debe tener exactamente un @ y al menos un punto después del @
        int arrobas = email.length() - email.replace("@", "").length();
        if (arrobas != 1) return false;

        int indexArroba = email.indexOf("@");
        // El @ no puede estar al inicio y debe haber un punto después
        return indexArroba > 0 && email.indexOf(".", indexArroba) > indexArroba + 1;
    }

    public static boolean validarPassword(String password) {
        return password.length() >= 6;
    }

    public static int calcularPuntaje(int base, int combo) {
        return (combo % 3 == 0) ? base + 20 : base + 10;
    }
}
