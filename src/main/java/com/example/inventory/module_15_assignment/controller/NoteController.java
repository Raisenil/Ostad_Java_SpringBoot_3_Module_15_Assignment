package com.example.inventory.module_15_assignment.controller;

import com.example.inventory.module_15_assignment.dto.NoteRequest;
import com.example.inventory.module_15_assignment.dto.NoteResponse;
import com.example.inventory.module_15_assignment.model.Note;
import com.example.inventory.module_15_assignment.service.NoteService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteRequest request, Principal principal) {
        Note note = noteService.createNote(principal.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(note));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<NoteResponse> getNotes(Principal principal) {
        return noteService.getNotes(principal.getName()).stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public NoteResponse getNoteById(@PathVariable Long id, Principal principal) {
        return toResponse(noteService.getNoteById(id, principal.getName()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public NoteResponse updateNote(@PathVariable Long id, @RequestBody NoteRequest request, Principal principal) {
        return toResponse(noteService.updateNote(id, principal.getName(), request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, Principal principal) {
        noteService.deleteNote(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleForbidden(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", exception.getMessage()));
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getOwner().getUsername());
    }
}

