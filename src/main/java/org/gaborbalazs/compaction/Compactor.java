package org.gaborbalazs.compaction;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class Compactor {

    List<Integer> compress(List<Integer> transactionIds) {
        return transactionIds.stream().sorted(this::compare).toList();
    }

    int compare(Integer x, Integer y) {
        return (x == 0) ? 1 : ((y == 0)) ? -1 : 0;
    }
}
