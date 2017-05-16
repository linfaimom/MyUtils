package com.mmmedu.home.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CodeLinesCounter {

    public long getOneFileCodeLines(String fileName) throws IOException {
        int temp = 0;
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.readLine() != null) {
                temp++;
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }
        return temp;
    }

    public long getAllFileCodeLines(String pathName, String suffix) throws IOException {
        long total = 0;
        File path = new File(pathName);
        if (path.isFile()) {
            total += getOneFileCodeLines(path.getAbsolutePath());
        } else if (path.isDirectory()) {
            File[] fileLists = path.listFiles(file -> file.getName().endsWith(suffix));
            for (File file : fileLists) {
                total += getOneFileCodeLines(file.getAbsolutePath());
            }
            File[] directories = path.listFiles(direcotory -> direcotory.isDirectory());
            for (File directory : directories) {
                total += getAllFileCodeLines(directory.getAbsolutePath(), suffix);
            }
        } else {
            return 0;
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the directory path: ");
        String directoryPath = scanner.nextLine();
        System.out.println("please enter the file suffix that you wanner match, eg. \".java\": ");
        String suffix = scanner.nextLine();
        System.out.println();
        long lines = new CodeLinesCounter().getAllFileCodeLines(directoryPath, suffix);
        System.out.println("There are totally " + lines + " lines of your " + "\"" + suffix + "\" files !");
    }
}
