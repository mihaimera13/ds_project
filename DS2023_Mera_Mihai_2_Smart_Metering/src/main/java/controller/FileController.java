package controller;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileController {

    private final File file;
    private final CSVReader fileReader;

    public FileController(String filePath) {
        CSVReader fileReader1;
        this.file = new File(filePath);

        try {
            fileReader1 = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            fileReader1 = null;
            e.printStackTrace();
        }


        this.fileReader = fileReader1;
    }

    public String readLine() {
        if (fileReader == null) {
            return null;
        }
        else{
            String[] nextLine;
            try {
                nextLine = fileReader.readNext();
            } catch (Exception e) {
                nextLine = null;
                e.printStackTrace();
            }
            if (nextLine != null) {
                return nextLine[0];
            }
            else {
                return null;
            }
        }
    }
}
