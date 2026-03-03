package org.gaborbalazs.compaction;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CompactionService {

    private final Compactor compactor;

    @Cacheable("cache1")
    @PreAuthorize("hasRole('ADMIN')")
    TransactionBatch serve(TransactionBatch transactionBatch) {
        return new TransactionBatch(compactor.compress(transactionBatch.transactionIds()));
    }
}
