package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.LoginDto;
import andrianianafn.gmi_api.dto.request.ProviderRequestDto;
import andrianianafn.gmi_api.dto.response.AuthResponseDto;
import andrianianafn.gmi_api.dto.response.ProviderResponseDto;
import andrianianafn.gmi_api.exceptions.RessourceNotFoundException;
import andrianianafn.gmi_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) throws RessourceNotFoundException {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<ProviderResponseDto> users(@RequestBody ProviderRequestDto providerRequestDto){
        return  new ResponseEntity<>(authService.usersProvider(providerRequestDto),HttpStatus.OK);
    }
}
