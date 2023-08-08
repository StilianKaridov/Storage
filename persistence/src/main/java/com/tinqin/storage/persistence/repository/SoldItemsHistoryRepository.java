package com.tinqin.storage.persistence.repository;

import com.tinqin.storage.persistence.entity.SoldItemsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoldItemsHistoryRepository extends JpaRepository<SoldItemsHistory, UUID> {
    boolean existsByUserId(UUID userId);
}
