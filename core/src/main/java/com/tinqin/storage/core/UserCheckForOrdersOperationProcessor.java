package com.tinqin.storage.core;

import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersOperation;
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersRequest;
import com.tinqin.storage.api.operations.usercheckfororders.UserCheckForOrdersResponse;
import com.tinqin.storage.persistence.repository.SoldItemsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCheckForOrdersOperationProcessor implements UserCheckForOrdersOperation {

    private final SoldItemsHistoryRepository soldItemsHistoryRepository;

    @Autowired
    public UserCheckForOrdersOperationProcessor(SoldItemsHistoryRepository soldItemsHistoryRepository) {
        this.soldItemsHistoryRepository = soldItemsHistoryRepository;
    }

    @Override
    public UserCheckForOrdersResponse process(UserCheckForOrdersRequest input) {
        boolean hasOrders = this.soldItemsHistoryRepository.existsByUserId(UUID.fromString(input.getUserId()));

        return UserCheckForOrdersResponse
                .builder()
                .hasOrders(hasOrders)
                .build();
    }
}
