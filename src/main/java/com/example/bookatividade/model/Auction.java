package com.example.bookatividade.model;

import com.example.bookatividade.model.enums.AuctionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_AUCTION")
@SequenceGenerator(name = "tb_auction_seq", sequenceName = "tb_auction_seq", allocationSize = 1)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_auction_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "starting_price")
    private BigDecimal startingPrice;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AuctionStatus status;

    // Relacionamento Many:Many com Book
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "auctions")
    private Set<Book> books;
}

