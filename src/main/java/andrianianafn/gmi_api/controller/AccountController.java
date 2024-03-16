package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
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

}
