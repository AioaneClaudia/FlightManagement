package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.service.NoticeBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService boardService = new NoticeBoardService();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("boards", boardService.getAllNoticeBoards());
        return "noticeboard/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("board", new NoticeBoard("", ""));
        return "noticeboard/form";
    }

    @PostMapping
    public String create(@ModelAttribute NoticeBoard board) {
        boardService.addNoticeBoard(board);
        return "redirect:/noticeboards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        boardService.removeNoticeBoard(id);
        return "redirect:/noticeboards";
    }
}
