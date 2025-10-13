package br.com.gritti.shared.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRoleRequest(
        @NotBlank
        @Size(min = 3, max = 100, message = "O nome da role deve ter entre 3 e 100 caracteres")
        String name,
        @NotBlank
        @Size(min = 2, max = 100, message = "A descrição da role deve ter entre 2 e 100 caracteres")
        String description
) {
}
