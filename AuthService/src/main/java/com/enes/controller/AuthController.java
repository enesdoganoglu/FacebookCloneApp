package com.enes.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("Giriş yapıldı");
    }
}
