package org.gaborbalazs.compaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CompactorTest {

    private static final Integer NUM_0 = 0;
    private static final Integer NUM_1 = 1;
    private static final Integer NUM_2 = 2;
    private static final Integer NUM_3 = 3;

    @InjectMocks
    private Compactor underTest;

    @Test
    void compress() {
        List<Integer> requestList = createList(NUM_2, NUM_0, NUM_0, NUM_3, NUM_0, NUM_1, NUM_0);
        List<Integer> expectedList = createList(NUM_2, NUM_3, NUM_1, NUM_0, NUM_0, NUM_0, NUM_0);

        List<Integer> result = underTest.compress(requestList);

        assertEquals(expectedList, result);
    }

    private List<Integer> createList(Integer... integers) {
        return List.of(integers);
    }
}