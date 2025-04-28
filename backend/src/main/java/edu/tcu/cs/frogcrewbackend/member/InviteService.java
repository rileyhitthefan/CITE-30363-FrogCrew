package edu.tcu.cs.frogcrewbackend.member;

import edu.tcu.cs.frogcrewbackend.member.converter.UserToLoginDTOConverter;
import edu.tcu.cs.frogcrewbackend.member.dto.LoginDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class InviteService {
    private final JwtDecoder jwtDecoder;
    private final UserToLoginDTOConverter userToLoginDTOConverter;

    public InviteService(JwtDecoder jwtDecoder, UserToLoginDTOConverter userToLoginDTOConverter) {
        this.jwtDecoder = jwtDecoder;
        this.userToLoginDTOConverter = userToLoginDTOConverter;
    }

    public List<String> sendInvite(List<String> emails){
        for (String email : emails) {
            // send invitation
        }
        return emails;
    }

    public boolean validateInviteToken(String token) {
        try {
            Jwt decodedJwt = jwtDecoder.decode(token);
            return true; // If decoding succeeds
        } catch (JwtException e) {
            return false; // If token is invalid or expired
        }
    }

}
