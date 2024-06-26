# Steps for Spring 6 demo code:

### 1. Go to localhost:9090/
Throws access denied exception and redirects to:
http://localhost:9090/oauth2/authorization/login-client

The "/login-client" is specified in application.yml as the subfield name under "registration"

The /oauth2/authorization/login-client path seems to be the default "login page". In this iteration the SecurityConfig.java was commented out. SecurityConfig.java has the following line:
```
// return http
//                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
//                .oauth2Login(oath2Login -> oath2Login.loginPage("/oauth2/authorization/login-client"))
//                .oauth2Client(Customizer.withDefaults())
//                .build();
```
And the oauth2Login() method has a default implementation which is redundantly specified here. It is the default implementation because this code was commented out but the login url remained the same

### 2. Client (9090) creates a redirect url to the auth server

http://authserver:9000/oauth2/authorize?response_type=code&client_id=login-client&scope=openid%20profile&state=uBI8YpAjPHb5VtBadE5XmzENz48jO2LlN4_6Uk6DnJE%3D&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/login-client&nonce=iHpSRLI6_2FCzeguTADVM6e64o15TwUoLF1X36qwfM8

Compared with the demo url from Spring in Action:
http://localhost:9000/oauth2/authorize?response_type=code&client_id=taco-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/taco-admin-client&scope=writeIngredients deleteIngredients

Differences:
- client_id is different. This is specified in yaml field key name under "registration"
- The book example does not have params for nonce and state
Redirect URL compared with yaml specs:
Used client-id: &client_id=login-client
Used scopes: &scope=openid%20profile
Used redirect url: &redirect_uri=http://127.0.0.1:9090/login/oauth2/code/login-client
Didn't use: provider, client-secret, client-authorization-method, authorization-grant-type
Added: state, nonce

Settings as compared with Registered Client in auth server:
Shared:
- Client Id string
- Client Secret
- Client Authentication Method String
- Authorization Grant Type String
- First redirect url of the Registered Client repo
- Both scopes
Client yaml is missing:
- Second redirect url: http://127.0.0.1:9090/authorized
Auth server Registered Client repo is missing:
- Provider. This seems to be substituted in the client yaml with the provider details field which contains:
    authorization-uri: http://authserver:9000/oauth2/authorize
    token-uri: http://authserver:9000/oauth2/token
    jwk-set-uri: http://authserver:9000/oauth2/jwt
- Using "issuer-uri" replaces the above 3

### 3. Now on auth server (9000), enter username and password
1) Auth server stores Security Context impl with a UsernamePasswordAuthenticationToken containing the tacochef user
successful login requires:
- Every kv pair in the Client yaml vs Auth Server config must match exactly
- Within each set of kv pairs, the client-id doesn't have to be the same. Putting "client-id: login-client-kv" and redirect uri with "login-client-90" still works
- The name of the yaml field under "registration" doesn't seem to matter. It only changes the Client's auth url, as mentioned above

2) Generates generic consent page
3) Validate consent page and generates and saves authorization code
4) Redirects to:
http://127.0.0.1:9090/login/oauth2/code/login-client?code=uU61TU42JeVzKodzt9QSnjg7YzJ7s9xf-zWRadPfvfCR-HjmYlpzwxWBKLLQGKBOZ38ToAiFKjixk6VHOPriZqeSfLbREeJ2X2RGE0o859nDIRvpMJSWOq8E_hbhDJRy&state=uBI8YpAjPHb5VtBadE5XmzENz48jO2LlN4_6Uk6DnJE%3D

This is the same format as the Spring in Action example code returned from the auth server

### 4. Back on client (9090)
1) Securing GET for:
/login/oauth2/code/login-client?code=uU61TU42JeVzKodzt9QSnjg7YzJ7s9xf-zWRadPfvfCR-HjmYlpzwxWBKLLQGKBOZ38ToAiFKjixk6VHOPriZqeSfLbREeJ2X2RGE0o859nDIRvpMJSWOq8E_hbhDJRy&state=uBI8YpAjPHb5VtBadE5XmzENz48jO2LlN4_6Uk6DnJE%3D
2) Without any filters, receive the following error: 

Error: Failed to process authentication request
org.springframework.security.oauth2.core.OAuth2AuthenticationException: [authorization_request_not_found]

From official Spring Security reference, requires a LoopbackIpRedirectFilter in Client to permit "localhost" domain

3) Client services can now run and sent REST requests to API service




