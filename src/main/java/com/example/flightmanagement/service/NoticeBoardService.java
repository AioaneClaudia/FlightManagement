package com.example.flightmanagement.service;

import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.repository.NoticeBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardService {
    private final NoticeBoardRepository boardRepository;

    public NoticeBoardService(NoticeBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void addNoticeBoard(NoticeBoard board) {
        boardRepository.save(board);
    }

    public List<NoticeBoard> getAllNoticeBoards() {
        return boardRepository.findAll();
    }

    public NoticeBoard getNoticeBoardById(String id) {
        return boardRepository.findById(id).orElse(null);
    }

    public void removeNoticeBoard(String id) {
        boardRepository.deleteById(id);
    }
}
