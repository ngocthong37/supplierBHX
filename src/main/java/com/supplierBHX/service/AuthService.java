package com.supplierBHX.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supplierBHX.Enum.TokenType;
import com.supplierBHX.entity.*;
import com.supplierBHX.repository.AccountRepository;
import com.supplierBHX.repository.TokenRepository;
import com.supplierBHX.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    EmailService emailService;

    @Autowired
    AccountRepository accountRepository;

    private final AccountRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Supplier supplier = new Supplier();
        supplier.setId(request.getSupplierId());
        var password = generateRandomPassword(10);
        var user = Account.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(password))
                .role(request.getRole())
                .status(request.getStatus())
                .email(request.getEmail())
                .supplier(supplier)
                .build();
        var savedUser = repository.save(user);
        // send account information to supplier's email
        if (savedUser.getPassword() != null) {
            String[] cc = {};
            Map<String, Object> model = new HashMap<>();
            model.put("userName", savedUser.getUsername());
            model.put("password", password);
            emailService.sendMail(savedUser.getEmail(), cc, "Tài khoản truy cập website của bạn đã được tạo", model);
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .accountId(user.getId())
                .userName(user.getUsername())
                .supplierId(user.getSupplier().getId())
                .build();
    }

    private void saveUserToken(Account account, String jwtToken) {
        var token = Token.builder()
                .account(account)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Account account) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(account.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUsername(refreshToken);
        if (userName != null) {
            var user = this.repository.findByUserName(userName)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public static String generateRandomPassword(int length) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLowerCase)
                .build();
        return pwdGenerator.generate(length);
    }

    public ResponseEntity<Object> updatePassword(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (json == null || json.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Empty JSON", ""));
            }
            JsonNode jsonObjectUpdate = objectMapper.readTree(json);
            Integer accountId = jsonObjectUpdate.get("accountId") != null ?
                    jsonObjectUpdate.get("accountId").asInt() : -1;
            String enteredPassword = jsonObjectUpdate.get("oldPassword") != null ?
                    jsonObjectUpdate.get("oldPassword").asText() : "";
            String newPassword = jsonObjectUpdate.get("newPassword") != null ?
                    jsonObjectUpdate.get("newPassword").asText() : "";
            Optional<Account> optionalAccount = accountRepository.findById(accountId);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (passwordEncoder.matches(enteredPassword, account.getPassword())) {
                    account.setPassword(passwordEncoder.encode(newPassword));
                    accountRepository.save(account);
                    return ResponseEntity.ok(new ResponseObject("SUCCESS", "Password updated", ""));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ResponseObject("ERROR", "Incorrect old password", ""));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("ERROR", "Account not found", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred", e.getMessage()));
        }
    }
}
