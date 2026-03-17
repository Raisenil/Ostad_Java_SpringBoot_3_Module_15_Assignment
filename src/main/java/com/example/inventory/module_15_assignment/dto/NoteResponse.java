package com.example.inventory.module_15_assignment.dto;

public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private String ownerUsername;

    public NoteResponse(Long id, String title, String content, String ownerUsername) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.ownerUsername = ownerUsername;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }
}

