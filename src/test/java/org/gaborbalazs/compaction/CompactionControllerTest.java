package org.gaborbalazs.compaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
class CompactionControllerTest {

    private static final String API_COMPACTION_URL = "/api/compaction";
    private static final String REQUEST_COMPACTION_JSON = "/request/compaction.json";
    private static final String REQUEST_COMPACTION_EMPTY_LIST_JSON = "/request/compaction-empty-list.json";
    private static final String RESPONSE_COMPACTION_JSON = "/response/compaction.json";
    private static final String RESPONSE_COMPACTION_VALIDATION_FAILED_JSON = "/response/compaction-validation-failed.json";
    private static final String USER = "user";
    private static final String ADMIN = "admin";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ResourceReader resourceReader;

    @Test
    void shouldReturn401WhenUnauthenticated() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void shouldReturn403WhenAuthenticatedAndValidationSucceededButUnauthorized() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .with(httpBasic(USER, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void shouldReturn200WhenAuthenticatedAndValidationSucceededAndAuthorized() throws Exception {
        TransactionBatch expected = objectMapper.readValue(resourceReader.read(new ClassPathResource(RESPONSE_COMPACTION_JSON)), TransactionBatch.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .with(httpBasic(ADMIN, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        TransactionBatch result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionBatch.class);

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(expected, result);
    }

    @Test
    void shouldReturn400WhenAuthenticatedButValidationFailed() throws Exception {
        TransactionBatch expected = objectMapper.readValue(resourceReader.read(new ClassPathResource(RESPONSE_COMPACTION_VALIDATION_FAILED_JSON)), TransactionBatch.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_COMPACTION_URL)
                .with(httpBasic(USER, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resourceReader.read(new ClassPathResource(REQUEST_COMPACTION_EMPTY_LIST_JSON)));

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        TransactionBatch result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TransactionBatch.class);

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        assertEquals(expected, result);
    }
}
