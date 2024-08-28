package org.example.atividadespringsecurity.domain.user;

public enum UserRole {
    ADMIN("admin"), DOCTOR("doctor"), PATIENT("patient"), RECEPTIONIST("receptionist");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    String getRole() {
        return this.role;
    }

    String getFullRoleString() {
        return "ROLE_" + this.getRole().toUpperCase();
    }
}
