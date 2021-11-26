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
            attributes = new ObjectMapper().readValue(json, HashMap.class);
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
            attributes = new ObjectMapper().readValue(json, HashMap.class);
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
            attributes = new ObjectMapper().readValue(json, HashMap.class);
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
            attributes = new ObjectMapper().readValue(json, HashMap.class);
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
        List<List<HashMap<String, Object>>> listOfListsOfAttributes = new ArrayList<>();
        List<String> errorFiles = reader.read(listOfListsOfAttributes);

        return peopleService.toString();
    }
}
