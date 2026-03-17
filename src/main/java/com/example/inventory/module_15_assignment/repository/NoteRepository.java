package com.example.inventory.module_15_assignment.repository;

import com.example.inventory.module_15_assignment.entity.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByOwnerUsername(String username);

    Optional<Note> findByIdAndOwnerUsername(Long id, String username);
}

