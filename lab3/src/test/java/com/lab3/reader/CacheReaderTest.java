package com.lab3.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.internal.HandleAndService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CacheReaderTest {

    CacheReader reader = CacheReader.INSTANCE;

    @BeforeEach
    void createFiles() throws IOException {
        //Создаем валидный файл для чтения
        File fileWithAttributes = new File("C:\\JavaLabs\\lab3\\src\\main\\resources\\toRead\\1.txt");
        if (!fileWithAttributes.exists())
            fileWithAttributes.createNewFile();
        new ObjectMapper().writeValue(fileWithAttributes, Map.of("S1", Map.of("fullname",
                "Ivanov Ivan Ivanovich",
                "birth year",
                1990,
                "phone number",
                89991231212L,
                "average marks",
                Map.of("coding",  5.00))));

        // И поломанный
        File brokenFileWithAttributes = new File("C:\\JavaLabs\\lab3\\src\\main\\resources\\toRead\\Broken1.txt");
        if (!brokenFileWithAttributes.exists())
            brokenFileWithAttributes.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(brokenFileWithAttributes.getAbsolutePath()));
        bufferedWriter.write("{" +
                "\"average marks\":{" +
                "\"coding\":5.0" +
                "}," +
                "\"fullname\":\"Ivanov Ivan Ivanovich\"," +
                "\"phone number\":89991231212," +
                "\"birth year\"}" +
                "]\n");
        bufferedWriter.close();
    }

    @Test
    void read() throws IOException {
        Map<String, Map <String, Map<String, Object>>> listOfListsOfAttributes = new HashMap();
        Map<String, List<String>> errorFiles = reader.read(listOfListsOfAttributes);
        //Кол-во прочитанных фалов : 1
        assertEquals(1, listOfListsOfAttributes.size());
        //Кол-во проблемных файлов : 1
        assertEquals(1, errorFiles.size());
    }
}