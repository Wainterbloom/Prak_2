package Num1;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;


public class AESDecryptor {
    public static void main(String[] args) {
        // Указание пути к зашифрованному файлу и файлу с ключом
        String encryptedFilePath = "C:\\Users\\student\\IdeaProjects\\Prak_2\\src\\Num1\\secret_text.enc";
        String keyFileName = "C:\\Users\\student\\IdeaProjects\\Prak_2\\src\\Num1\\aes.key";
        try {

            FileInputStream keyInputStream = new FileInputStream(keyFileName); // Чтение ключа из файла
            byte[] keyAndIV = new byte[32]; // Создание массива для хранения ключа и вектора инициализации

            keyInputStream.read(keyAndIV); // Чтение ключа и вектора инициализации из файла
            // Создание объекта ключа из прочитанных данных
            Key key = new SecretKeySpec(keyAndIV, 16, 16, "AES"); // Создание объекта ключа из прочитанных данных

            IvParameterSpec ivSpec = new IvParameterSpec(keyAndIV, 0, 16); // Создание объекта вектора инициализации из прочитанных данных

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding"); // Создание объекта Cipher для расшифровки

            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, ivSpec.getIV()));
            // Инициализация Cipher для режима дешифрования с помощью ключа и вектора инициализации

            FileInputStream encryptedInputStream = new FileInputStream(encryptedFilePath); // Чтение зашифрованных данных из файла

            byte[] encryptedData = new byte[encryptedInputStream.available()]; // Создание массива для хранения зашифрованных данных и чтение данных из файла
            encryptedInputStream.read(encryptedData);

            byte[] decryptedData = cipher.doFinal(encryptedData); // Расшифровка данных с использованием Cipher

            FileOutputStream decryptedOutputStream = new FileOutputStream("decrypted_output.txt");

            decryptedOutputStream.write(decryptedData);

            File decryptedFile = new File("decrypted_output.txt");
            byte[] decryptedFileContent = new byte[(int)decryptedFile.length()];
            FileInputStream fileInputStream = new FileInputStream(decryptedFile);
            fileInputStream.read(decryptedFileContent);
            fileInputStream.close();

            String decryptedText = new String(decryptedFileContent, StandardCharsets.UTF_8); // Преобразование расшифрованных данных в текст
            System.out.println("Расшифрованный текст из файла:");
            System.out.println(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
