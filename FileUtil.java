/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filefinder;

import static java.lang.System.out;

import java.io.*;

import java.util.regex.*;

import java.util.ArrayList;

/**
 *
 * @author juliazhao
 */
public class FileUtil {

    public static void getAllfilesInFolder(String path) {
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                out.print("file: ");
            } else {
                out.print("dictetory: ");
            }
            out.println(file.getName());
        }

    }

    public static void recursivelyCheck(File folder, Pattern ContentPattern, Pattern filePattern) {
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                if (matchFileName(file.getName(), filePattern)) {
                    ArrayList<String> fileLines = new ArrayList<String>();
                    try {
                        fileLines = readLines(file);
                    } catch (IOException e) {
                        out.println("I am wrong");
                    }
                    checkLines(file, fileLines, ContentPattern);
                }

            } else {
                recursivelyCheck(file, ContentPattern, filePattern);
            }

        }

    }

    public static boolean matchFileName(String filename, Pattern pattern) {
        Matcher m = pattern.matcher(filename);
        return m.find();
    }

    public static ArrayList<String> readLines(File file)
            throws IOException {

        ArrayList<String> result = new ArrayList<String>();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);

        String line = reader.readLine();
        while (line != null) {
            result.add(line);
            line = reader.readLine();
        }
        return result;
    }

    public static void printLines(File file) {
        ArrayList<String> fileLines = new ArrayList<String>();
        try {
            fileLines = readLines(file);
        } catch (IOException e) {
            out.println("I am wrong");
        }
        for (String s : fileLines) {
            out.println(s);
        }
    }

    public static void checkLines(File file, ArrayList<String> lines, Pattern pattern) {
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                out.println(file.getAbsolutePath() + ":" + line);
            }
        }
    }

    public static void findContent(String path, Pattern ContentPattern, Pattern filePattern) {
        File folder = new File(path);
        recursivelyCheck(folder, ContentPattern, filePattern);
    }

}
