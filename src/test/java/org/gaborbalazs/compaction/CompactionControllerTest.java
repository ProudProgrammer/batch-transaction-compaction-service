package org.gaborbalazs.compaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CompactionControllerTest {

    private static final String API_COMPACTION_URL = "/api/compaction";
    private static final String REQUEST_COMPACTION_JSON = "/request/compaction.json";
    private static final String REQUEST_COMPACTION_EMPTY_LIST_JSON = "/request/compaction-empty-list.json";
    private static final String RESPONSE_COMPACTION_JSON = "/response/compaction.json";
    private static final String RESPONSE_COMPACTION_VALIDATION_FAILED_JSON = "/response/compaction-validation-failed.json";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResourceReader resourceReader;

    @Test
    void shouldReturn200WhenValidationSucceeded() throws Exception {
        TransactionBatch expected = objectMapper.readValue(resourceReader.read(new ClassPathResource(RESPONSE_COMPACTION_JSON)), TransactionBatch.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        TransactionBatch result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionBatch.class);

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(expected, result);
    }

    @Test
    void shouldReturn400WhenValidationFailed() throws Exception {
        TransactionBatch expected = objectMapper.readValue(resourceReader.read(new ClassPathResource(RESPONSE_COMPACTION_VALIDATION_FAILED_JSON)), TransactionBatch.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_EMPTY_LIST_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        TransactionBatch result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionBatch.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals(expected, result);
    }
}
