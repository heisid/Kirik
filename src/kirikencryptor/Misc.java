package kirikencryptor;

public class Misc {
    // Reminder for myself: % in java is different with python
    // in java it means reminder, not modulo!
    public static int mod(int n, int m) {
        return (((n % m) + m) % m);
    }

}
