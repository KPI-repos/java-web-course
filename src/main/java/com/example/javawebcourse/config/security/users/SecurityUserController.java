package com.example.javawebcourse.config.security.users;


import com.example.javawebcourse.config.security.jwt.JwtCookieFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
class SecurityUserController {

    private final JwtCookieFactory jwtCookieFactory;

    private final SecurityUserService userService;

    private final SecurityUserMapper securityUserMapper;

    protected record TokenResponse(String token, int expirationTime) {
    }

    protected record LoginRequest(String username, String password) {
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        if (!userService.isValidLogin(loginRequest.username(), loginRequest.password())) {
            return ResponseEntity.badRequest().body(null);
        }

        var cookies = jwtCookieFactory.cookForUser(loginRequest.username());
        for (var c : cookies) {
            response.addCookie(c);
        }

        var responseEntityBuilder = ResponseEntity.ok();

        var token = cookies.get(0).getValue() + cookies.get(1).getValue();
        return responseEntityBuilder.body(new TokenResponse(
            token,
            cookies.getFirst().getMaxAge()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated PostSecurityUserDTO dto, Principal principal) {
        if (principal != null) {
            return ResponseEntity.badRequest().body("User is already logged in");
        }
        var entity = securityUserMapper.toEntity(dto);
        return ResponseEntity.ok(securityUserMapper.toDTO(userService.save(entity)));
    }

    @GetMapping("/me")
    public ResponseEntity<GetSecurityUserDTO> me(Principal principal) {
        return ResponseEntity.ok(securityUserMapper.toDTO(userService.findByUsername(principal.getName()).orElseThrow(() ->
            // this should never happen
            new IllegalStateException("User with username %s not found".formatted(principal.getName()))
        )));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> delete(Principal principal) {
        userService.deleteByUsername(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me")
    public ResponseEntity<GetSecurityUserDTO> update(
        @RequestBody @Validated PostSecurityUserDTO dto,
        Principal principal) {
        var entity = userService.findByUsername(principal.getName()).orElseThrow(() ->
            new EntityNotFoundException("User with username %s not found".formatted(principal.getName()))
        );
        securityUserMapper.mapNonNullIntoEntity(dto, entity);
        return ResponseEntity.ok(securityUserMapper.toDTO(userService.update(entity)));
    }

}
