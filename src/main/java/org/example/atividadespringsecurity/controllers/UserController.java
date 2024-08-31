package org.example.atividadespringsecurity.controllers;

import org.example.atividadespringsecurity.domain.user.User;
import org.example.atividadespringsecurity.domain.user.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @GetMapping("/my-info")
    public ResponseEntity<UserInfoDTO> getCurrentUserInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoDTO userInfoDTO = new UserInfoDTO(user.getUsername(), user.getRole());
        return ResponseEntity.ok(userInfoDTO);
    }
}
