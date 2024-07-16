package com.example.securityproject;

import com.example.securityproject.entities.Role;
import com.example.securityproject.entities.User;
import com.example.securityproject.repository.RoleRepository;
import com.example.securityproject.repository.UserRepository;
import com.example.securityproject.security.models.Client;
import com.example.securityproject.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.test.annotation.Commit;

import java.util.UUID;

@SpringBootTest
public class InsertUserTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @Test
    @Commit
    public void insertNewUserToDb() {
         User user = new User();
         user.setEmail("divyanshu@gmail.com");
         user.setPassword(passwordEncoder.encode("12345"));
         user.addRole(roleRepository.findByName("ROLE_USER"));
         userRepository.save(user);
    }

    @Test
    @Commit
    public void updateUser(){
        User user = userRepository.findById(4L).get();
        user.addRole(roleRepository.findByName("userAdmin"));
        userRepository.save(user);
    }

    @Test
    @Commit
    public void addRole(){
        Role role = new Role();
        role.setName("userAdmin");
        roleRepository.save(role);
    }

    @Test
    @Commit
    public void deleteRole(){
        roleRepository.deleteById(3L);
    }
    @Test
    @Commit
    public void deleteUser(){
        userRepository.deleteById(3L);
    }

    @Test
    @Commit
    public void insertNewClient(){
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("productservice")
                .clientSecret(passwordEncoder.encode("productservicepassword"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        jpaRegisteredClientRepository.save(oidcClient);
    }
}
