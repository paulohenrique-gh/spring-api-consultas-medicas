package org.example.atividadespringsecurity.domain.user;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(@NotNull String username, @NotNull String password) { }
