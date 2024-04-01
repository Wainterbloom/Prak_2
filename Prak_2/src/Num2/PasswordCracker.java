package Num2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCracker {

    static String targetHash = "2a2375e1171723a0e04a3c49adccb4ec6db86b2f7527db45e0bb84d8d76a9b9d3536d39e01b92d303fc966b36aa73475f9aea541d63f5ad894a50dda63b68a1c"; //пароля, который нужно взломать
    static char[] charset = "abcdefghijklmnopqrstuvwxyz".toCharArray(); // Символы, которые могут использоваться в пароле
    static int passwordLength = 5; // Длина пароля
    static StringBuilder password = new StringBuilder("aaaaa"); // Начальное предположение пароля

    public static void main(String[] args) {
        findPassword(0);
    }

    // Рекурсивная функция для поиска пароля
    public static void findPassword(int index) {
        if (index == passwordLength) {
            // Проверяем пароль, если он достиг заданной длины
            String hashedPassword = getSHA512Hash(password.toString());
            if (hashedPassword.equals(targetHash)) {
                System.out.println("Пароль найден: " + password);
                return;
            }
        } else {
            // Генерируем все возможные комбинации символов пароля и продолжаем поиск
            for (char c : charset) {
                password.setCharAt(index, c);
                findPassword(index + 1);
            }
        }
    }

    // Метод для хеширования пароля с использованием алгоритма SHA-512
    private static String getSHA512Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            // Преобразуем байтовый массив хэша в строку шестнадцатеричных символов
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
