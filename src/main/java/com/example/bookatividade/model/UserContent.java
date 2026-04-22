package com.example.bookatividade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_CONTENT")
@SequenceGenerator(name = "tb_userc_seq", sequenceName = "tb_userc_seq", allocationSize = 1)
public class UserContent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_userc_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "pathPhoto")
    private String pathPhoto;
}
