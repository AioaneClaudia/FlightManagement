package com.example.flightmanagement.service;

import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.repository.NoticeBoardRepository;
import java.util.List;

public class NoticeBoardService {
    private NoticeBoardRepository boardRepository = new NoticeBoardRepository();

    public void addNoticeBoard(NoticeBoard board) {
        boardRepository.save(board);
    }

    public List<NoticeBoard> getAllNoticeBoards() {
        return boardRepository.findAll();
    }

    public NoticeBoard getNoticeBoardById(String id) {
        return boardRepository.findById(id);
    }

    public void removeNoticeBoard(String id) {
        boardRepository.delete(id);
    }
}
