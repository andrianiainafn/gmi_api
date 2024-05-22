package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.request.AddRoleRequestDto;
import andrianianafn.gmi_api.dto.request.CreateAccountRequest;
import andrianianafn.gmi_api.dto.request.EditProfileDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.dto.response.ProfileResponseDto;
import andrianianafn.gmi_api.dto.response.UserListDto;
import andrianianafn.gmi_api.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<AccountInfoResponseDto> createNewAccount(@RequestBody AccountRequestDto accountRequestDto){
        return new ResponseEntity<>(accountService.createNewAccount(accountRequestDto),HttpStatus.CREATED);
    }

    @GetMapping("/{email-or-name}")
    public ResponseEntity<List<AccountInfoResponseDto>> searchAccount(@PathVariable ("email-or-name") String emailOrName){
        return  new ResponseEntity<>(accountService.getAccountByEmailOrName(emailOrName),HttpStatus.OK);
    }
    @GetMapping("/info")
    public ResponseEntity<AccountInfoResponseDto> getAccountInfo(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(accountService.getAccountInfo(token),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/list")
    public ResponseEntity<UserListDto> getUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(accountService.getUserList(page,size,token),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/role")
    public ResponseEntity<AccountInfoResponseDto> addRoleToAccount(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody AddRoleRequestDto addRoleRequestDto){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(accountService.addRoleToUser(token,addRoleRequestDto.getRolesId()),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfileInfo(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(accountService.getProfile(token,size,page),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{user-id}")
    public ResponseEntity<AccountInfoResponseDto> editAccountInfo (@PathVariable ("user-id") String userId,@RequestBody AccountRequestDto accountRequestDto){
        return new ResponseEntity<>(accountService.editAccountInfo(userId,accountRequestDto),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity <AccountInfoResponseDto> editProfileInfo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader) throws IOException {
          EditProfileDto editProfileDto = EditProfileDto.builder()
                  .firstname(firstname)
                  .email(email)
                  .file(file)
                  .lastname(lastname)
                  .build();
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(accountService.editProfile(token,editProfileDto),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
