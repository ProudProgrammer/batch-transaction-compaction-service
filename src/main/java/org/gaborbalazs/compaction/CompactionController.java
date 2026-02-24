package org.gaborbalazs.compaction;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class CompactionController {

    private final CompactionService compactionService;

    @PostMapping ("api/compaction")
    List<Integer> compaction(@RequestBody TransactionBatch transactionBatch) {
        return compactionService.serve(transactionBatch);
    }
}
