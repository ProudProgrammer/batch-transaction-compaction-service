package org.gaborbalazs.compaction;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

record TransactionBatch(@NotEmpty(message = "Transaction id list must not be empty") List<Integer> transactionIds) {
}
