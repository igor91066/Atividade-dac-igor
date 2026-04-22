package com.example.bookatividade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_BOOK")
@SequenceGenerator(name = "tb_book_seq", sequenceName = "tb_book_seq", allocationSize = 1)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_book_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "pages")
    private Integer pages;

    // Relacionamento Many:1 com User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_FK")
    private User user;

    // Relacionamento Many:Many com Auction
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "TB_AUCTION_BOOK",
            joinColumns = @JoinColumn(name = "book_fk"),
            inverseJoinColumns = @JoinColumn(name = "auction_fk"))
    private Set<Auction> auctions;
}
