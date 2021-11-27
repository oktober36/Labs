package com.lab3.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnotherCacheReader {
    static final File FOLDER = new File("C:\\JavaLabs\\lab3\\src\\main\\resources\\toRead");

    private static AnotherCacheReader instance;
    private AnotherCacheReader(){}
    public static AnotherCacheReader getInstance(){
        if(instance == null){
            instance = new AnotherCacheReader();
        }
        return instance;
    }
    public Map<String, List<String>> read(Map<String, Map<String, Map<String, Object>>> listOfListsOfAttributes) throws IOException {
        Map<String, List<String>> errorFiles = new HashMap<>();

        if (FOLDER.listFiles().length == 0) return null;

        for (File file : FOLDER.listFiles()) {
            try {
                Map <String, Map <String, Object>> listOfAttributes =
                        new ObjectMapper().readValue(file, Map.class);
                listOfListsOfAttributes.put(file.getName(), listOfAttributes);
            } catch (JsonProcessingException e) {
                errorFiles.put(file.getName(), List.of("Неправильно задан json"));
            } finally {
                file.delete();
            }
        }
        return errorFiles;
    }
}
