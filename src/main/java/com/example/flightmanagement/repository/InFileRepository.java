package com.example.flightmanagement.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class InFileRepository<ID, T> implements IRepository<ID, T> {

    protected Map<ID, T> entities = new HashMap<>();
    private final File file;
    private final ObjectMapper objectMapper;
    private final Class<T> type; // um Objekte beim Lesen zu erstellen

    public InFileRepository(String filePath, Class<T> type) {
        this.file = new File(filePath);
        this.type = type;
        this.objectMapper = new ObjectMapper();


        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        loadFromFile();
    }

    private void loadFromFile() {
        try {
            if (file.exists() && file.length() > 0) {
                List<T> list = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
                for (T entity : list) {
                    @SuppressWarnings("unchecked")
                    ID id = (ID) entity.getClass().getMethod("getId").invoke(entity);
                    entities.put(id, entity);
                }
            } else {
                saveToFile(); // falls Datei leer ist, erstelle leere Liste
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Datei: " + file.getPath(), e);
        }
    }

    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>(entities.values()));
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Schreiben in die Datei: " + file.getPath(), e);
        }
    }

    @Override
    public void save(T entity) {
        try {
            @SuppressWarnings("unchecked")
            ID id = (ID) entity.getClass().getMethod("getId").invoke(entity);
            entities.put(id, entity);
            saveToFile();
        } catch (Exception e) {
            throw new RuntimeException("Entity muss eine getId() Methode haben", e);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public T findById(ID id) {
        return entities.get(id);
    }

    @Override
    public void delete(ID id) {
        entities.remove(id);
        saveToFile();
    }
}

