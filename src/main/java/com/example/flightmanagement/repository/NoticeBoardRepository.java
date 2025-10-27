package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.NoticeBoard;
import java.util.*;

public class NoticeBoardRepository {
    private Map<String, NoticeBoard> boards = new HashMap<>();

    public void save(NoticeBoard board) {
        boards.put(board.getId(), board);
    }

    public List<NoticeBoard> findAll() {
        return new ArrayList<>(boards.values());
    }

    public NoticeBoard findById(String id) {
        return boards.get(id);
    }

    public void delete(String id) {
        boards.remove(id);
    }
}
