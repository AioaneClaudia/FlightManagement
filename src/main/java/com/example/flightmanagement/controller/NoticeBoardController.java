package com.example.flightmanagement.controller;

import com.example.flightmanagement.model.NoticeBoard;
import com.example.flightmanagement.service.NoticeBoardService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller pentru NoticeBoard - CRUD + validări (field + business).
 * Rutele și template-urile presupuse:
 * - list:     GET  /noticeboards            -> noticeboard/index
 * - new form: GET  /noticeboards/new        -> noticeboard/form
 * - create:   POST /noticeboards            -> redirect /noticeboards
 * - edit:     GET  /noticeboards/{id}/edit  -> noticeboard/form
 * - update:   POST /noticeboards/{id}      -> redirect /noticeboards
 * - details:  GET  /noticeboards/{id}/details -> noticeboard/details
 * - delete:   POST /noticeboards/{id}/delete -> redirect /noticeboards (sau return details dacă eroare)
 */
@Controller
@RequestMapping("/noticeboards")
public class NoticeBoardController {

    private final NoticeBoardService boardService;

    public NoticeBoardController(NoticeBoardService boardService) {
        this.boardService = boardService;
    }

    // Lista tuturor noticeboard-urilor
    @GetMapping
    public String list(
            Model model,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        List<NoticeBoard> boards = boardService.getFilteredAndSorted(id, start, end, sortField, sortDir);

        model.addAttribute("boards", boards);

        // păstrează valorile filtrelor
        model.addAttribute("idFilter", id);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        // păstrează sortarea
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "noticeboard/index";
    }


    // Form pentru creare
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("board", new NoticeBoard("", ""));
        return "noticeboard/form";
    }

    // Creare cu validare (field + business)
    @PostMapping
    public String create(@Valid @ModelAttribute("board") NoticeBoard board,
                         BindingResult result,
                         Model model) {

        // Business validation: id unic
        if (board.getId() != null && !board.getId().isBlank()) {
            if (boardService.getNoticeBoardById(board.getId()) != null) {
                result.rejectValue("id", "exists", "A NoticeBoard with this ID already exists");
            }
        }

        if (result.hasErrors()) {
            return "noticeboard/form";
        }

        boardService.addNoticeBoard(board);
        return "redirect:/noticeboards";
    }

    // Form pentru editare
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        NoticeBoard existing = boardService.getNoticeBoardById(id);
        if (existing == null) {
            // dacă nu există, redirecționăm la list
            return "redirect:/noticeboards";
        }
        model.addAttribute("board", existing);
        return "noticeboard/form";
    }

    // Update cu validare
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("board") NoticeBoard board,
                         BindingResult result,
                         Model model) {

        NoticeBoard existing = boardService.getNoticeBoardById(id);
        if (existing == null) {
            // nu putem actualiza ceva inexistent -> redirect la list
            return "redirect:/noticeboards";
        }

        // Forțăm id-ul din path (câmpul din form este readonly la edit)
        board.setId(id);

        // Poți adăuga validări business suplimentare aici (ex: unicitate alt camp)
        if (result.hasErrors()) {
            return "noticeboard/form";
        }

        boardService.addNoticeBoard(board); // save = insert/update
        return "redirect:/noticeboards";
    }

    // Detalii
    @GetMapping("/{id}/details")
    public String details(@PathVariable String id, Model model,
                          @ModelAttribute("message") String message) {
        NoticeBoard board = boardService.getNoticeBoardById(id);
        if (board == null) {
            return "redirect:/noticeboards";
        }
        model.addAttribute("board", board);
        // Dacă ai trimis mesaje prin RedirectAttributes le poți afișa în view (ex: mesaj de eroare la ștergere)
        if (message != null && !message.isBlank()) {
            model.addAttribute("infoMessage", message);
        }
        return "noticeboard/details";
    }

    // Ștergere - validare business: nu se șterge dacă există flights asociate
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        NoticeBoard board = boardService.getNoticeBoardById(id);
        if (board == null) {
            return "redirect:/noticeboards";
        }

        if (board.getFlightsOfTheDay() != null && !board.getFlightsOfTheDay().isEmpty()) {
            // nu ștergem și întoarcem utilizatorul la pagina de detalii cu mesaj de eroare
            String msg = "Cannot delete NoticeBoard that has associated Flights. Remove or reassign flights first.";
            // folosim redirectAttributes pentru a trece mesajul pe redirect
            redirectAttributes.addFlashAttribute("message", msg);
            return "redirect:/noticeboards/" + id + "/details";
        }

        boardService.removeNoticeBoard(id);
        return "redirect:/noticeboards";
    }
}
