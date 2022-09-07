package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tasks")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    private String description;

    private LocalDateTime created = LocalDateTime.now();

    @NonNull
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

}
