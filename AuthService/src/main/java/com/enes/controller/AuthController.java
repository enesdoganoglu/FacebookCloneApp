package com.enes.controller;

import com.enes.dto.request.LoginRequestDto;
import com.enes.dto.request.RegisterRequestDto;
import com.enes.dto.response.LoginResponseDto;
import com.enes.dto.response.RegisterResponseDto;
import com.enes.exception.AuthException;
import com.enes.exception.ErrorType;
import com.enes.repository.entity.Auth;
import com.enes.service.AuthService;
import com.enes.utility.TokenCreator;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenCreator tokenCreator;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto){

            Optional<Auth> auth = authService.doLogin(dto);
        if(auth.isEmpty())
            return ResponseEntity.ok(LoginResponseDto.builder()
                    .statusCode(4000)
                    .message("Kullanıcı adı veya şifre hatalı")
                    .build());

        return ResponseEntity.ok(LoginResponseDto.builder()
                .statusCode(2001)
                .message(tokenCreator.createToken(auth.get().getId()))
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        if(!dto.getPassword().equals(dto.getRepassowrd()))
            throw new AuthException(ErrorType.ERROR_PASSWORD);
        authService.register(dto);
        return ResponseEntity.ok(RegisterResponseDto.builder()
                .statusCode(2000)
                .message("Kayıt işlemi baraşılır şekilde gerçekleşti. Lütfen E-Pasta" +
                        " adresinize gelen aktivasyon linkine tıklayınız.")
                .build());
    }
}
