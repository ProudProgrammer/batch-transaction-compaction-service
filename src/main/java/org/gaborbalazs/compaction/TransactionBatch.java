package org.gaborbalazs.compaction;

import java.util.List;

public record TransactionBatch(List<Integer> transactionIds) {
}
