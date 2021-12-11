package com.vet24.web.controllers.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthRequest implements Serializable {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
