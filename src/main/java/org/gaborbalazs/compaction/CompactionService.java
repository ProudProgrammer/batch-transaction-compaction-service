package org.gaborbalazs.compaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CompactionService {

    private final Compactor compactor;

    List<Integer> serve(TransactionBatch transactionBatch) {
        return compactor.compress(transactionBatch.transactionIds());
    }
}
