package com.tacos.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class AutenticationTests {

    private static final String CLIENT_ID = "taco-admin-client";
    private static final String CLIENT_SECRET = "secret";
    private static final String REDIRECT_URI = "http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void performTokenRequestWhenValidClientCredentialsThenOkay() throws Exception {
        // Log in and get to consent page to get "state" value
        MvcResult mvcResult = this.mockMvc.perform(get("/oauth2/authorize")
                        .with(oauth2Login().authorities(new SimpleGrantedAuthority("writeIngredients")))
                        .queryParam("response_type", "code")
                        .queryParam("scope", "writeIngredients")
                        .queryParam("client_id", CLIENT_ID))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Document doc = Jsoup.parse(contentAsString);
        String state = doc.select("input[name=state]").attr("value");

        // Post in consent page with "state" value
        MvcResult authenticatedResult = this.mockMvc.perform(post("/oauth2/authorize")
                        .with(oauth2Login().authorities(new SimpleGrantedAuthority("writeIngredients")))
                        .param("client_id", CLIENT_ID)
                        .param("scope", "writeIngredients")
                        .param("state", state))
                .andReturn();
        // Response from consent page is a blank page with redirect URL containing "code"
        String redirectedUrl = authenticatedResult.getResponse().getRedirectedUrl();
        UriComponents uri = UriComponentsBuilder.fromUri(URI.create(redirectedUrl)).build();
        MultiValueMap<String, String> queryParams = uri.getQueryParams();
        String code = queryParams.get(OAuth2AuthorizationResponseType.CODE.getValue()).get(0);

        // Post for oauth2 token using grand_type of authorization_code
        this.mockMvc.perform(post("/oauth2/token")
                        .with(oauth2Login().authorities(new SimpleGrantedAuthority("writeIngredients")))
                        .param("grant_type", "authorization_code")
                        .param("redirect_uri", REDIRECT_URI)
                        .param("code", code)
                        .with(httpBasic(CLIENT_ID, CLIENT_SECRET)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isString())
                .andExpect(jsonPath("$.expires_in").isNumber())
                .andExpect(jsonPath("$.scope").value("writeIngredients"))
                .andExpect(jsonPath("$.token_type").value("Bearer"));

    }

}
