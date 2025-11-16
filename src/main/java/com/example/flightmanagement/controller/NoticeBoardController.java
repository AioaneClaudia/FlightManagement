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

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("board", boardService.getNoticeBoardById(id));
        return "noticeboard/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute NoticeBoard board) {
        board.setId(id);
        boardService.addNoticeBoard(board); // save = update
        return "redirect:/noticeboards";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("board", boardService.getNoticeBoardById(id));
        return "noticeboard/details";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        boardService.removeNoticeBoard(id);
        return "redirect:/noticeboards";
    }
}
