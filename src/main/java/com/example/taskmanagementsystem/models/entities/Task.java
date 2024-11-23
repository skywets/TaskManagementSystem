package com.example.taskmanagementsystem.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne()
    @JoinColumn(name = "authorId")
    private User author;
    @ManyToOne()
    @JoinColumn(name = "performerId")
    private User performer;

}
