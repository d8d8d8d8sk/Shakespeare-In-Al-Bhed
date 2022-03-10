package com.example.AlBhedTranslator;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileToText {
    private final Map<String, String> albhed;

    public FileToText() throws IOException {
        albhed = matrixToMap();
        String result = englishToAlbhed(
                fileToText(Path.of("***YOUR INPUT HERE"))
        );
        Files.writeString(Path.of("***YOUR OUTPUT HERE***"), result);
    }

    public String englishToAlbhed(String input){
        //the string value of stuff is redundant, should do a hashmap of Characters instead but too lazy
        StringBuilder english = new StringBuilder("");
        for (int i = 0; i < input.length(); i++){
            if (albhed.get(String.valueOf(input.charAt(i))) == null){
                english.append(input.charAt(i));
            } else {
                english.append(albhed.get(String.valueOf(input.charAt(i))));
            }
        }
        return english.toString();
    }

    public String fileToText(Path path) throws IOException {
        return Files.readString(path);
    }

    public Map<String, String> matrixToMap(){
        var matrix = allChars();
        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < matrix.get(0).size(); i++){
            map.put(matrix.get(0).get(i), matrix.get(1).get(i));
        }
        for (int i = 0; i < matrix.get(0).size(); i++){
            map.put(matrix.get(2).get(i), matrix.get(3).get(i));
        }
        return map;
    }

    public List<List<String>> allChars(){
        //english upper, albhed upper, english lower, albhed lower
        List<List<String>> matrix = new ArrayList<>();
        matrix.add(stringToCharList(englishUpper()));
        matrix.add(stringToCharList(albhedUpper()));
        matrix.add(stringToCharList(englishLower()));
        matrix.add(stringToCharList(albhedLower()));
        return matrix;
    }

    public List<String> stringToCharList(String input){
        List<String> list = new ArrayList<>();
        for (int i = 0 ;i < input.length(); i++){
            list.add(String.valueOf(input.charAt(i)));
        }
        return list;
    }

    public String englishUpper(){
        return "EPSTIWKNUVGCLRYBXHMDOFZQAJ";
    }

    public String englishLower(){
        return englishUpper().toLowerCase(Locale.ROOT);
    }

    public String albhedUpper(){
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    public String albhedLower(){
        return albhedUpper().toLowerCase(Locale.ROOT);
    }
}
