package org.gaborbalazs.compaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableCaching
@SpringBootTest
class CompactionServiceTest {

    @MockitoBean
    private Compactor compactor;
    @Autowired
    private CompactionService underTest;

    @Test
    @WithMockUser(roles = "ADMIN")
    void compactorCompressShouldBeCalledOnceWhenCacheIsEnabled() {
        TransactionBatch transactionBatch = new TransactionBatch(Collections.singletonList(1));

        underTest.serve(transactionBatch);
        underTest.serve(transactionBatch);

        verify(compactor, times(1)).compress(transactionBatch.transactionIds());
    }
}