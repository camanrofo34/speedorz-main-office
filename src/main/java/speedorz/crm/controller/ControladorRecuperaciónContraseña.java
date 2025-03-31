package speedorz.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import speedorz.crm.services.ServicioUsuario;

@RestController
public class ControladorRecuperaciónContraseña {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/recover-password")
    public String recoverPassword(@RequestParam String email) {
        boolean success = servicioUsuario.sendRecoveryCode(email);
        return success ? "Correo de recuperación enviado" : "Error al enviar el correo de recuperación";
    }
}