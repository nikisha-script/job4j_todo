package ru.job4j.todo.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "priorities")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    private String name;

    @NonNull
    private int position;

}
