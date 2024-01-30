package com.tacos.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutenticationTests {

    private static final String CLIENT_ID = "taco-admin-client";
    private static final String CLIENT_SECRET = "secret";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void performTokenRequestWhenValidClientCredentialsThenOkay() throws Exception {
        mockMvc.perform(post("/oauth2/token")
                        .param("grant_type", "client_credentials")
                        .param("scope", "writeIngredients")
                        .with(httpBasic(CLIENT_ID, CLIENT_SECRET)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.access_token").isString(),
                        jsonPath("$.expires_in").isNumber(),
                        jsonPath("$.scope").value("writeIngredients"),
                        jsonPath("$.token_type").value("Bearer"));

    }

}
