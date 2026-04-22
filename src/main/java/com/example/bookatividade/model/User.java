package com.example.bookatividade.model;

import com.example.bookatividade.model.enums.GenderType;
import com.example.bookatividade.model.enums.StatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "date_birth", nullable = false)
    private LocalDate dateBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    // Relacionamento 1:1 com UserContent
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "ID_USER_CONTENT_FK", unique = true)
    private UserContent userContent;

    // Relacionamento Many:Many com Role
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    // Relacionamento 1:Many com Book
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Book> books;
}
