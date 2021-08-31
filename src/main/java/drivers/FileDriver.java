/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 *
 * @author zofia-px
 */
public class FileDriver {
    private FileReader reader;
    private BufferedReader buffer;
    private FileWriter writer;
    private PrintWriter printer;
    private String line;
    private String fileContent;
    private FileOutputStream output;
    private InputStream input;

    public String readInputFile(File file) throws FileNotFoundException, IOException {
        this.fileContent = "";
        reader = new FileReader(file);
        buffer = new BufferedReader(reader);
        while((this.line = buffer.readLine()) != null) {
            this.fileContent += line + "\n";
        }   
        return fileContent;
    }
    
    public void saveFileContent(String content, File file) throws IOException {
        this.writer = new FileWriter(file);
        this.printer = new PrintWriter(writer);
        printer.print(content);
        printer.flush();
        printer.close();
    }
    
    
}
