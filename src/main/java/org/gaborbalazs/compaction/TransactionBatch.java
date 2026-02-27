package org.gaborbalazs.compaction;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

record TransactionBatch(@NotEmpty(message = "Transaction Id List must not be empty") List<Integer> transactionIds) {
}
