package kirikencryptor;

public class Shifter {
    private static final int START_CHAR = ' ';
    private static final int END_CHAR = '~';
    private static final int RANGE = (END_CHAR - START_CHAR + 1);
    private final String mode;
    private final String data;
    private final int dataLength;
    private final char key;
    private String result;

    public Shifter(String mode, char key, String data) {
        this.mode = mode;
        this.key = key;
        this.data = data;
        this.dataLength = data.length();
    }

    // Reminder for myself: % in java is different with python
    // in java it means reminder, not modulo!
    private static int mod(int n, int m) {
        return (((n % m) + m) % m);
    }

    private void encrypt() {
        char[] resultArray = new char[this.dataLength];
        for (int i = 0; i < this.dataLength; i++) {
            int charData = this.data.charAt(i);
            int charResult = charData;
            if (START_CHAR <= charData && charData <= END_CHAR) {
                charResult = (mod(((charData - START_CHAR) + this.key), RANGE) + START_CHAR);
            }
            resultArray[i] = (char) charResult;
        }
        this.result = new String(resultArray);
    }

    private void decrypt() {
        char[] resultArray = new char[this.dataLength];
        for (int i = 0; i < this.dataLength; i++) {
            int charData = this.data.charAt(i);
            int charResult = charData;
            if (START_CHAR <= charData && charData <= END_CHAR) {
                charResult = mod(((charData - START_CHAR) - this.key), RANGE) + START_CHAR;
            }
            resultArray[i] = (char) charResult;
        }
        this.result = new String(resultArray);
    }

    public String getResult() {
        if ("dec".equals(this.mode)) {
            decrypt();
        } else {
            encrypt();
        }
        return this.result;
    }
}
