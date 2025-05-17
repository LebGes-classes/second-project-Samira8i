package util;

import java.util.Random;

public class IdGenerator {
    private static final Random random = new Random();

    public static int generateId() {
        // Генерируем положительное 6-значное число (от 100000 до 999999)
        return 100000 + random.nextInt(900000);
    }
}