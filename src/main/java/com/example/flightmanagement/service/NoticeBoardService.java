package com.example.flightmanagement.service;

import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.repository.NoticeBoardRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    // Filtrare combinată după id și interval de date
    public List<NoticeBoard> getFilteredAndSorted(String idFilter,
                                                  String startDate,
                                                  String endDate,
                                                  String sortField,
                                                  String sortDir) {
        List<NoticeBoard> list = boardRepository.findAll();

        // FILTRARE după ID
        if (idFilter != null && !idFilter.isBlank()) {
            String lower = idFilter.toLowerCase();
            list = list.stream()
                    .filter(b -> b.getId() != null && b.getId().toLowerCase().contains(lower))
                    .collect(Collectors.toList());
        }

        // FILTRARE după interval de date (date ca String YYYY-MM-DD)
        if ((startDate != null && !startDate.isBlank()) || (endDate != null && !endDate.isBlank())) {
            list = list.stream()
                    .filter(b -> {
                        String d = b.getDate();
                        boolean afterStart = (startDate == null || startDate.isBlank()) || d.compareTo(startDate) >= 0;
                        boolean beforeEnd = (endDate == null || endDate.isBlank()) || d.compareTo(endDate) <= 0;
                        return afterStart && beforeEnd;
                    })
                    .collect(Collectors.toList());
        }

        // SORTARE
        Comparator<NoticeBoard> comp;
        switch (sortField) {
            case "date":
                comp = Comparator.comparing(NoticeBoard::getDate);
                break;
            default:
                comp = Comparator.comparing(NoticeBoard::getId);
                break;
        }
        if ("desc".equals(sortDir)) {
            comp = comp.reversed();
        }

        return list.stream().sorted(comp).collect(Collectors.toList());
    }
}
