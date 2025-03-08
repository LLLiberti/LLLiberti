package com.software;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String origPath = args[0];
        String plagiarizedPath = args[1];
        String outputPath = args[2];

        checkPlagiarism.checkPlagiarism(origPath, plagiarizedPath, outputPath);
    }
}