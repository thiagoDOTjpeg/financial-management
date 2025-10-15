package br.com.gritti.shared.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 3, max = 100, message = "Username deve ter entre 3 e 100 caracteres")
        String username,
        @Email(message = "Email inválido")
        String email,
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        String password,
        @Size(min = 6, message = "O nome completo deve ter no mínimo ter 6 caracteres")
        String fullName
) { }
