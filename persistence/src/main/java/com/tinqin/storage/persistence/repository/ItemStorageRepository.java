package com.tinqin.storage.persistence.repository;

import com.tinqin.storage.persistence.entity.ItemStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemStorageRepository extends JpaRepository<ItemStorage, UUID> {

    Optional<ItemStorage> findFirstByItemId(UUID itemId);

    List<ItemStorage> findByItemIdIn(Collection<UUID> ids);
}
