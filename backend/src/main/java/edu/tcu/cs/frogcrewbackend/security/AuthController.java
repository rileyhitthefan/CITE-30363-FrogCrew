package edu.tcu.cs.frogcrewbackend.security;

import edu.tcu.cs.frogcrewbackend.system.Result;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            
            // Create login info with JWT token
            Map<String, Object> loginInfo = this.authService.createLoginInfo(authentication);
            return new Result(true, StatusCode.SUCCESS, "Login success", loginInfo);
        } catch (AuthenticationException e) {
            LOGGER.error("Authentication failed for user: {}", loginRequest.email(), e);
            return new Result(false, StatusCode.UNAUTHORIZED, "Invalid credentials", null);
        }
    }

    @PostMapping("/me")
    public Result getLoginInfo(Authentication authentication) {
        LOGGER.debug("Authenticated user: {}", authentication.getName());
        return new Result(true, StatusCode.SUCCESS, "User info retrieved", this.authService.createLoginInfo(authentication));
    }

    @GetMapping("/validate/{token}")
    public Result validateInviteToken(@PathVariable String token) {
        boolean isValid = this.authService.validateInviteToken(token);

        if (isValid) {
            Map<String, String> tokenData = new HashMap<>();
            tokenData.put("token", token);
            return new Result(true, StatusCode.SUCCESS, "Token valid", tokenData);
        } else {
            return new Result(false, StatusCode.NOT_FOUND, "Token not valid", null);
        }
    }

    // Record for login request
    public record LoginRequest(String email, String password) {}
}
