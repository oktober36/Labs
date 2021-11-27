package com.lab3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.reader.CacheReader;
import com.lab3.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class Controller {
    private final PeopleService peopleService;

    @Autowired
    public Controller(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PutMapping
    @ResponseBody
    public String createPerson(@RequestBody String json) throws IOException {
        Map<String,Object> attributes;
        try {
            attributes = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        }
        peopleService.create(attributes);
        return peopleService.toString();
    }

    @GetMapping
    @ResponseBody
    public String getPerson(@RequestBody String json) throws IOException {
        Map<String, Object> attributes;
        try {
            attributes = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        }
        return peopleService.get(attributes);
    }


    @PostMapping
    @ResponseBody
    public String updatePerson(@RequestBody String json) throws IOException {
        Map<String, Object> attributes;
        try {
            attributes = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        }
        peopleService.update(attributes);
        return peopleService.toString();
    }

    @DeleteMapping
    @ResponseBody
    public String deletePerson(@RequestBody String json) throws IOException {
        Map<String, Object> attributes;
        try {
            attributes = new ObjectMapper().readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IOException("Неправильно задан json");
        }
        peopleService.delete(attributes);
        return peopleService.toString();
    }
    @PutMapping("/readCache")
    @ResponseBody
    public String readCache() throws IOException {
        CacheReader reader = CacheReader.INSTANCE;
        StringBuilder report = new StringBuilder();
        Map<String, Map <String, Map<String, Object>>> listOfListsOfAttributes = new HashMap();
        Map<String, List<String>> errorFiles = reader.read(listOfListsOfAttributes);
        if (listOfListsOfAttributes.size() != 0) {
            for (String fileName : listOfListsOfAttributes.keySet()) {
                if (errorFiles.keySet().contains(fileName)) continue;
                for (String personName : listOfListsOfAttributes.get(fileName).keySet()) {
                    try {
                        peopleService.create(listOfListsOfAttributes.get(fileName).get(personName));
                    } catch (IOException e) {
                        StringBuilder errorReport = new StringBuilder();
                        if (!errorFiles.keySet().contains(fileName)) errorFiles.put(fileName, new ArrayList<>());
                        errorReport.append(personName);
                        errorReport.append(" : ");
                        errorReport.append(e.getMessage());
                        errorFiles.get(fileName).add(errorReport.toString());
                    }
                }
            }
        }
        if (errorFiles.size() != 0) {
            report.append("Ошибки :\n");
            for (String filename : errorFiles.keySet()) {
                report.append("Файл ");
                report.append(filename);
                report.append("\n");
                for (String error : errorFiles.get(filename)) {
                    report.append(error);
                    report.append("\n");
                }
            }
            report.append("\n");
        } else if (listOfListsOfAttributes.size() == 0) throw new IOException("Папка пустая");
        report.append(peopleService.toString());
        return report.toString();
    }
}
