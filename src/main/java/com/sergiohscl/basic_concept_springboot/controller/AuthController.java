package com.sergiohscl.basic_concept_springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergiohscl.basic_concept_springboot.Service.UserService;
import com.sergiohscl.basic_concept_springboot.model.User;
import com.sergiohscl.basic_concept_springboot.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Edpoints for user authentication management")
public class AuthController {

    private final UserService userService;
    

    public AuthController(UserService userService) {
        this.userService = userService;
        
    }

    @PostMapping("/register")
    @Operation(summary = "Adds a new user",
        description = "Adds a new user by passing in a JSON, XML or YML representation of the person.",
        tags = {"Auth"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = @Content(schema = @Schema(implementation = User.class))
                ),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        User user = userService.registerUser(request.get("username"), "password");
        return ResponseEntity.ok(user);       
    }

    @PostMapping("/login")
    @Operation(
        summary = "Authenticates user and returns a JWT token",
        description = "Validates user credentials and returns a JWT token upon successful authentication.",
        tags = {"Auth"},
        responses = {
            @ApiResponse(
                description = "Token generated",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                description = "Invalid credentials",
                responseCode = "401",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Optional<User> user = userService.searchByUsername(request.get("username"));
        if (user.isPresent() && user.get().getPassword().equals(request.get("password"))) {
            String token = JwtUtil.generateToken(user.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }     
        return ResponseEntity.status(401).body("Invalid credentials!");
    }    
  
}
