package com.lab3.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum CacheReader {
    INSTANCE;

    static final File FOLDER = new File("C:\\JavaLabs\\lab3\\src\\main\\resources\\toRead");


    public List<String> read(List<List<HashMap<String, Object>>> listOfListsOfAttributes) {
        List<String> errorFiles = new ArrayList<>();

        if (FOLDER.listFiles().length == 0) return null;

        for (File file : FOLDER.listFiles()) {
            try {
                List<HashMap<String, Object>> listOfAttributes = List.of(
                        new ObjectMapper().readValue(file.getAbsolutePath(), HashMap[].class));
                listOfListsOfAttributes.add(listOfAttributes);
            } catch (JsonProcessingException e) {
                errorFiles.add(file.getName());
            } finally {
                file.delete();
            }
        }
        return errorFiles;
    }
}
