package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> createNewAccount(@RequestBody AccountRequestDto accountRequestDto){
        accountService.createNewAccount(accountRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{email-or-name}")
    public ResponseEntity<List<AccountInfoResponseDto>> searchAccount(@PathVariable ("email-or-name") String emailOrName){
        return  new ResponseEntity<>(accountService.getAccountByEmailOrName(emailOrName),HttpStatus.OK);
    }

}
