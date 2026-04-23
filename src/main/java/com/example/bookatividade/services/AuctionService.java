package com.example.bookatividade.services;

import com.example.bookatividade.model.Auction;
import com.example.bookatividade.model.enums.AuctionStatus;
import com.example.bookatividade.respositories.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    // CREATE
    public Auction createAuction(Auction auction) {
        if (auction.getDateStart() != null && auction.getDateEnd() != null) {
            if (auction.getDateStart().isAfter(auction.getDateEnd())) {
                throw new IllegalArgumentException("Data de início não pode ser após a data de término");
            }
        }
        return auctionRepository.save(auction);
    }

    // READ - Buscar por ID
    public Optional<Auction> getAuctionById(Long id) {
        return auctionRepository.findById(id);
    }

    // READ - Buscar todos
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    // READ - Buscar leilões por status
    public List<Auction> getAuctionsByStatus(AuctionStatus status) {
        return auctionRepository.findByStatusOrderByDateStartAsc(status);
    }

    // READ - Buscar leilões ativos
    public List<Auction> getActiveAuctions() {
        return auctionRepository.findActiveAuctions(LocalDate.now());
    }

    // READ - Buscar leilões expirados
    public List<Auction> getExpiredAuctions() {
        return auctionRepository.findExpiredAuctions(LocalDate.now());
    }

    // READ - Buscar leilões por preço inicial maior que
    public List<Auction> getAuctionsByMinPrice(BigDecimal minPrice) {
        return auctionRepository.findByStartingPriceGreaterThan(minPrice);
    }

    // READ - Buscar leilões por preço inicial menor que
    public List<Auction> getAuctionsByMaxPrice(BigDecimal maxPrice) {
        return auctionRepository.findByStartingPriceLessThan(maxPrice);
    }

    // READ - Buscar leilões por intervalo de datas
    public List<Auction> getAuctionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return auctionRepository.findByDateStartBetween(startDate, endDate);
    }

    // READ - Buscar leilões que já terminaram
    public List<Auction> getTerminatedAuctions() {
        return auctionRepository.findByDateEndBefore(LocalDate.now());
    }

    // READ - Buscar leilões que ainda não começaram
    public List<Auction> getNotStartedAuctions() {
        return auctionRepository.findByDateStartAfter(LocalDate.now());
    }

    // UPDATE
    public Auction updateAuction(Long id, Auction auctionDetails) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leilão não encontrado"));

        if (auctionDetails.getStartingPrice() != null) {
            auction.setStartingPrice(auctionDetails.getStartingPrice());
        }
        if (auctionDetails.getDateStart() != null) {
            auction.setDateStart(auctionDetails.getDateStart());
        }
        if (auctionDetails.getDateEnd() != null) {
            if (auctionDetails.getDateStart() != null &&
                auctionDetails.getDateStart().isAfter(auctionDetails.getDateEnd())) {
                throw new IllegalArgumentException("Data de início não pode ser após a data de término");
            }
            auction.setDateEnd(auctionDetails.getDateEnd());
        }
        if (auctionDetails.getStatus() != null) {
            auction.setStatus(auctionDetails.getStatus());
        }

        return auctionRepository.save(auction);
    }

    // UPDATE - Atualizar status
    public Auction updateAuctionStatus(Long id, AuctionStatus status) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leilão não encontrado"));
        auction.setStatus(status);
        return auctionRepository.save(auction);
    }

    // UPDATE - Atualizar preço inicial
    public Auction updateStartingPrice(Long id, BigDecimal price) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leilão não encontrado"));
        auction.setStartingPrice(price);
        return auctionRepository.save(auction);
    }

    // DELETE
    public void deleteAuction(Long id) {
        if (!auctionRepository.existsById(id)) {
            throw new IllegalArgumentException("Leilão não encontrado");
        }
        auctionRepository.deleteById(id);
    }

    // DELETE - Deletar todos
    public void deleteAllAuctions() {
        auctionRepository.deleteAll();
    }

    // HELPER - Contar leilões por status
    public Long countAuctionsByStatus(AuctionStatus status) {
        return auctionRepository.countByStatus(status);
    }

    // HELPER - Contar total de leilões
    public long countAuctions() {
        return auctionRepository.count();
    }

    // HELPER - Verificar se leilão está ativo
    public boolean isAuctionActive(Long id) {
        Optional<Auction> auction = auctionRepository.findById(id);
        if (auction.isEmpty()) {
            return false;
        }
        LocalDate today = LocalDate.now();
        Auction a = auction.get();
        return a.getStatus() == AuctionStatus.ACTIVE &&
               !a.getDateStart().isAfter(today) &&
               !a.getDateEnd().isBefore(today);
    }
}

