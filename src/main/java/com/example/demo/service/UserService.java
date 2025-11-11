package com.example.demo.service;

import com.example.demo.Enum.Role;
import com.example.demo.dto.RequestUserSignUpDTO;
import com.example.demo.entities.User;
import com.example.demo.exception.UserAlredyRegisteredException;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public void signUp(RequestUserSignUpDTO requestUserSignUpDTO, Map<String, Object> result) {
        if (userRepository.findByEmail(requestUserSignUpDTO.getEmail()).isPresent()) {
            throw new UserAlredyRegisteredException("Already register try to log in");
        }
        User user = new User();
        user.setName(requestUserSignUpDTO.getName());
        user.setEmail(requestUserSignUpDTO.getEmail());
        user.setPhoneNumber(requestUserSignUpDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(requestUserSignUpDTO.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        result.put("success", savedUser);
    }

    public void login(String object, Map<String, Object> result) {
        JSONObject jsonObject = new JSONObject(object);
        String email = jsonObject.has("email") ? jsonObject.getString("email") : "";
        String password = jsonObject.has("password") ? jsonObject.getString("password") : "";

        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw new RuntimeException("Fill the mandatory fields.");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Present"));
        String token = jwtService.generateToken(user);
        result.put("success", token);
    }
}
