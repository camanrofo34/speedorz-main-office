package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import speedorz.crm.services.ServicioUsuario;

@RestController
public class ControladorResetContraseña {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String recoveryCode, @RequestParam String newPassword) {
        boolean success = servicioUsuario.resetPassword(email, recoveryCode, newPassword);
        return success ? "Contraseña restablecida con éxito" : "Código de recuperación inválido o expirado";
    }
}