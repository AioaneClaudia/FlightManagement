package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.NoticeBoard;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeBoardRepository extends InFileRepository<String, NoticeBoard> {
    public NoticeBoardRepository() {
        super("src/main/resources/data/NoticeBoard.json", NoticeBoard.class);
    }
}