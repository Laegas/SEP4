package util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class METARTXTFilePreprocessor {

    private File file;
    private Scanner sc;
    private String filename;

    public METARTXTFilePreprocessor(String filename)
    {
        this.filename = filename;
        this.file= new File(filename);
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void preprocess() {

        List<String> lines = new ArrayList<>();

        String prev, curr;

        while (sc.hasNextLine()) {
            curr = sc.nextLine();
            if(curr.length() != 0) {
                if(curr.startsWith(" ")) {
                    curr = lines.get(lines.size()-1) + " " + curr.trim();;
                    lines.remove(lines.size()-1);
                    lines.add(curr);
                } else {
                    lines.add(curr);
                }
            }
        }

        for(String line : lines)
            System.out.println(line);
        /*
        try {
            Path file = Paths.get(filename);
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        */
    }
}
