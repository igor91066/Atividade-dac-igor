package com.example.bookatividade.controllers;

import com.example.bookatividade.model.Auction;
import com.example.bookatividade.model.enums.AuctionStatus;
import com.example.bookatividade.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    // CREATE - POST
    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        try {
            Auction createdAuction = auctionService.createAuction(auction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // READ - GET all
    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    // READ - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        Optional<Auction> auction = auctionService.getAuctionById(id);
        return auction.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - GET by status
    @GetMapping("/search/status/{status}")
    public ResponseEntity<List<Auction>> getAuctionsByStatus(@PathVariable AuctionStatus status) {
        List<Auction> auctions = auctionService.getAuctionsByStatus(status);
        return ResponseEntity.ok(auctions);
    }

    // READ - GET active auctions
    @GetMapping("/search/active")
    public ResponseEntity<List<Auction>> getActiveAuctions() {
        List<Auction> auctions = auctionService.getActiveAuctions();
        return ResponseEntity.ok(auctions);
    }

    // READ - GET expired auctions
    @GetMapping("/search/expired")
    public ResponseEntity<List<Auction>> getExpiredAuctions() {
        List<Auction> auctions = auctionService.getExpiredAuctions();
        return ResponseEntity.ok(auctions);
    }

    // READ - GET by minimum price
    @GetMapping("/search/min-price/{minPrice}")
    public ResponseEntity<List<Auction>> getAuctionsByMinPrice(@PathVariable BigDecimal minPrice) {
        List<Auction> auctions = auctionService.getAuctionsByMinPrice(minPrice);
        return ResponseEntity.ok(auctions);
    }

    // READ - GET by maximum price
    @GetMapping("/search/max-price/{maxPrice}")
    public ResponseEntity<List<Auction>> getAuctionsByMaxPrice(@PathVariable BigDecimal maxPrice) {
        List<Auction> auctions = auctionService.getAuctionsByMaxPrice(maxPrice);
        return ResponseEntity.ok(auctions);
    }

    // READ - GET by date range
    @GetMapping("/search/date-range")
    public ResponseEntity<List<Auction>> getAuctionsByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Auction> auctions = auctionService.getAuctionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(auctions);
    }

    // READ - GET terminated auctions
    @GetMapping("/search/terminated")
    public ResponseEntity<List<Auction>> getTerminatedAuctions() {
        List<Auction> auctions = auctionService.getTerminatedAuctions();
        return ResponseEntity.ok(auctions);
    }

    // READ - GET not started auctions
    @GetMapping("/search/not-started")
    public ResponseEntity<List<Auction>> getNotStartedAuctions() {
        List<Auction> auctions = auctionService.getNotStartedAuctions();
        return ResponseEntity.ok(auctions);
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody Auction auctionDetails) {
        try {
            Auction updatedAuction = auctionService.updateAuction(id, auctionDetails);
            return ResponseEntity.ok(updatedAuction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH status
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Auction> updateAuctionStatus(@PathVariable Long id, @PathVariable AuctionStatus status) {
        try {
            Auction updatedAuction = auctionService.updateAuctionStatus(id, status);
            return ResponseEntity.ok(updatedAuction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PATCH starting price
    @PatchMapping("/{id}/price/{price}")
    public ResponseEntity<Auction> updateStartingPrice(@PathVariable Long id, @PathVariable BigDecimal price) {
        try {
            Auction updatedAuction = auctionService.updateStartingPrice(id, price);
            return ResponseEntity.ok(updatedAuction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        try {
            auctionService.deleteAuction(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE all
    @DeleteMapping
    public ResponseEntity<Void> deleteAllAuctions() {
        auctionService.deleteAllAuctions();
        return ResponseEntity.noContent().build();
    }

    // HELPER - Count auctions by status
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countAuctionsByStatus(@PathVariable AuctionStatus status) {
        Long count = auctionService.countAuctionsByStatus(status);
        return ResponseEntity.ok(count);
    }

    // HELPER - Count all auctions
    @GetMapping("/count")
    public ResponseEntity<Long> countAuctions() {
        long count = auctionService.countAuctions();
        return ResponseEntity.ok(count);
    }

    // HELPER - Check if auction is active
    @GetMapping("/is-active/{id}")
    public ResponseEntity<Boolean> isAuctionActive(@PathVariable Long id) {
        boolean isActive = auctionService.isAuctionActive(id);
        return ResponseEntity.ok(isActive);
    }
}
