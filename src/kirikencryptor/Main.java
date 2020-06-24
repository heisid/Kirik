package kirikencryptor;

public class Main {
    private static final char START_CHAR = ' ';
    private static final char END_CHAR = '~';
    private static final char RANGE = (char) (END_CHAR - START_CHAR + 1);

    public static void main(String[] args) {
        String mode = "enc";
        char key = 0;
        String data = "";
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-mode":
                    mode = args[i + 1];
                    break;
                case "-key":
                    key = (char) Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    data = args[i + 1];
                    break;
                default:
                    System.out.println("I think you were entering the wrong arguments");
            }
        }

        String result = "";

        if (mode.equals("enc")) {
            result = encrypt(data, key);
        } else if (mode.equals("dec")) {
            result = decrypt(data, key);
        }

        System.out.println(result);
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