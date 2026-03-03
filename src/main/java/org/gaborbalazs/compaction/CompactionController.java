package org.gaborbalazs.compaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/compaction")
class CompactionController {

    private final CompactionService compactionService;

    @PostMapping
    TransactionBatch compaction(@RequestBody @Valid TransactionBatch transactionBatch) {
        return compactionService.serve(transactionBatch);
    }
}
