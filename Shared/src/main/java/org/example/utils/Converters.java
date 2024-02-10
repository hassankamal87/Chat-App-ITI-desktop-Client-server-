package org.example.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Converters {
    public static File convertBytesToFile(byte[] bytes, String fileName) throws IOException {
        String[] preSuf = fileName.split("\\.");
        String prefix = "file";
        String suffix = "txt";
        if(preSuf.length == 2 && preSuf[0].length()>2){
            prefix = preSuf[0];
            suffix = preSuf[1];
        }
        File tempFile = File.createTempFile(prefix, "."+suffix);
        try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            fileOutputStream.write(bytes);
        }
        return tempFile;
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        Path filePath = file.toPath();
        return Files.readAllBytes(filePath);
    }
}
