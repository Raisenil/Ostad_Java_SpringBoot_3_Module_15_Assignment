package com.example.inventory.module_15_assignment.service;

import com.example.inventory.module_15_assignment.dto.NoteRequest;
import com.example.inventory.module_15_assignment.model.AppUser;
import com.example.inventory.module_15_assignment.model.Note;
import com.example.inventory.module_15_assignment.model.Role;
import com.example.inventory.module_15_assignment.repository.NoteRepository;
import com.example.inventory.module_15_assignment.repository.UserRepository;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note createNote(String username, NoteRequest request) {
        AppUser owner = findUser(username);
        Note note = new Note();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        note.setOwner(owner);
        return noteRepository.save(note);
    }

    public List<Note> getNotes(String username) {
        AppUser currentUser = findUser(username);
        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return noteRepository.findAll();
        }
        return noteRepository.findByOwnerUsername(username);
    }

    public Note getNoteById(Long id, String username) {
        AppUser currentUser = findUser(username);
        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            return noteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("note not found"));
        }
        return noteRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new AccessDeniedException("note not found or forbidden"));
    }

    public Note updateNote(Long id, String username, NoteRequest request) {
        Note note = noteRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new AccessDeniedException("note not found or forbidden"));

        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id, String username) {
        AppUser currentUser = findUser(username);
        if (currentUser.getRole() == Role.ROLE_ADMIN) {
            Note note = noteRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("note not found"));
            noteRepository.delete(note);
            return;
        }

        Note note = noteRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new AccessDeniedException("note not found or forbidden"));
        noteRepository.delete(note);
    }

    private AppUser findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
    }
}

