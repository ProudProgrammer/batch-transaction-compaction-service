package org.gaborbalazs.compaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CompactionService {

    private final Compactor compactor;

    TransactionBatch serve(TransactionBatch transactionBatch) {
        return new TransactionBatch(compactor.compress(transactionBatch.transactionIds()));
    }
}
