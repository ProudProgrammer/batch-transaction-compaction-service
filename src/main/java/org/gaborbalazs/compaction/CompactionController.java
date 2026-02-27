package org.gaborbalazs.compaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class CompactionController {

    private final CompactionService compactionService;

    @PostMapping ("api/compaction")
    TransactionBatch compaction(@RequestBody @Valid TransactionBatch transactionBatch) {
        return compactionService.serve(transactionBatch);
    }
}
