package kirikencryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

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

        String result;

        Shifter shifter = new Shifter(mode, key, data.toString());
        result = shifter.getResult();

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

}
