package com.example.bookatividade.respositories;

import com.example.bookatividade.model.Auction;
import com.example.bookatividade.model.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    // Buscar leilões por status
    List<Auction> findByStatus(AuctionStatus status);

    // Buscar leilões por status ordenado por data de início
    List<Auction> findByStatusOrderByDateStartAsc(AuctionStatus status);

    // Buscar leilões com preço inicial maior que
    List<Auction> findByStartingPriceGreaterThan(BigDecimal price);

    // Buscar leilões com preço inicial menor que
    List<Auction> findByStartingPriceLessThan(BigDecimal price);

    // Buscar leilões por intervalo de datas
    List<Auction> findByDateStartBetween(LocalDate startDate, LocalDate endDate);

    // Buscar leilões que já terminaram
    List<Auction> findByDateEndBefore(LocalDate date);

    // Buscar leilões que ainda não começaram
    List<Auction> findByDateStartAfter(LocalDate date);

    // Buscar leilões vigentes (em andamento)
    @Query("SELECT a FROM Auction a WHERE a.status = 'ACTIVE' AND a.dateStart <= :today AND a.dateEnd >= :today")
    List<Auction> findActiveAuctions(@Param("today") LocalDate today);

    // Buscar leilões expirados
    @Query("SELECT a FROM Auction a WHERE a.dateEnd < :today")
    List<Auction> findExpiredAuctions(@Param("today") LocalDate today);

    // Contar leilões por status
    @Query("SELECT COUNT(a) FROM Auction a WHERE a.status = :status")
    Long countByStatus(@Param("status") AuctionStatus status);
}
