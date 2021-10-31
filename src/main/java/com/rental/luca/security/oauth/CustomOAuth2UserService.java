package com.rental.luca.security.oauth;

import com.rental.luca.security.authentication.AuthenticationProvider;
import com.rental.luca.security.authentication.User;
import com.rental.luca.security.authentication.UserRole;
import com.rental.luca.security.authentication.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        if (!userService.isAuthenticated(email)) {
            User user = new User(
                    oAuth2User.getAttribute("given_name"),
                    oAuth2User.getAttribute("name"),
                    email,
                    bCryptPasswordEncoder.encode(oAuth2User.getAttribute("sub")),
                    UserRole.USER_ROLE,
                    AuthenticationProvider.GMAIL);

            userService.addUser(user);
        }
        return new CustomOAuth2User(oAuth2User);
    }
}
