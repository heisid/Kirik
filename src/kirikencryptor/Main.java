package kirikencryptor;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static final char START_CHAR = ' ';
    private static final char END_CHAR = '~';
    private static final char RANGE = (char) (END_CHAR - START_CHAR + 1);

    public static void main(String[] args) {
        String mode = "enc";
        char key = 0;
        StringBuilder data = new StringBuilder();
        String inFileName = "";
        String outFileName = "";
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = (char) Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = new StringBuilder(args[i + 1]);
                    break;
                case "-in":
                    inFileName = args[i + 1];
                    break;
                case "-out":
                    outFileName = args[i + 1];
                    break;
                default:
                    System.out.println("I think you were entering the wrong arguments");
                    System.exit(-1);
            }
        }

        if (!inFileName.isEmpty() && (data.length() == 0)) {
            File inFile = new File(inFileName);
            try (Scanner reader = new Scanner(inFile)) {
                while (reader.hasNext()) {
                    data.append(reader.nextLine()).append("\n");
                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }

        String result = "";

        if (mode.equals("enc")) {
            result = encrypt(data.toString(), key);
        } else if (mode.equals("dec")) {
            result = decrypt(data.toString(), key);
        }

        if (outFileName.isEmpty()) {
            System.out.println(result);
        } else {
            File outFile = new File(outFileName);
            if (outFile.exists()) {
                System.out.println(outFileName + " already exists. Overwrite? [Y/N]");
                Scanner confirm = new Scanner(System.in);
                if (!"y".equals(confirm.next().toLowerCase())) {
                    System.exit(0);
                }
            }
            try (FileWriter writer = new FileWriter(outFile)) {
                writer.write(result);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String encrypt(String clearText, char key) {
        int length = clearText.length();
        char[] cipherArray = new char[length];
        for (int i = 0; i < length; i++) {
            char charClear = clearText.charAt(i);
            char charCipher = charClear;
            if (START_CHAR <= charClear && charClear <= END_CHAR) {
                charCipher = (char) ((((charClear - START_CHAR) + key) % RANGE) + START_CHAR);
            }
            cipherArray[i] = charCipher;
        }
        return new String(cipherArray);
    }

    private static String decrypt(String cipherText, char key) {
        int length = cipherText.length();
        char[] clearArray = new char[length];
        for (int i = 0; i < length; i++) {
            char charCipher = cipherText.charAt(i);
            char charClear = charCipher;
            if (START_CHAR <= charCipher && charCipher <= END_CHAR) {
                charClear = (char) ((((charCipher - START_CHAR) - key) % RANGE) + START_CHAR);
            }
            clearArray[i] = charClear;
        }
        return new String(clearArray);
    }
}
