package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.CreateAccountRequest;
import andrianianafn.gmi_api.dto.request.LoginDto;
import andrianianafn.gmi_api.dto.request.ProviderRequestDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.dto.response.AuthResponseDto;
import andrianianafn.gmi_api.dto.response.ProviderResponseDto;
import andrianianafn.gmi_api.exceptions.RessourceNotFoundException;
import andrianianafn.gmi_api.service.AccountService;
import andrianianafn.gmi_api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin (origins = "*")
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    public AuthController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) throws RessourceNotFoundException {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }
    @PostMapping("/user")
    public ResponseEntity<ProviderResponseDto> users(@RequestBody ProviderRequestDto providerRequestDto){
        return  new ResponseEntity<>(authService.usersProvider(providerRequestDto),HttpStatus.OK);
    }
    @PostMapping("/signin")
    public ResponseEntity<AccountInfoResponseDto> signin(@RequestBody CreateAccountRequest createAccountRequest){
        return new ResponseEntity<>(accountService.signin(createAccountRequest),HttpStatus.CREATED);
    }
}
