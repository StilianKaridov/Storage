package com.tinqin.storage.data.repository;

import com.tinqin.storage.data.entity.ItemStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemStorageRepository extends JpaRepository<ItemStorage, UUID> {

    Optional<ItemStorage> findFirstByItemId(UUID itemId);
}
