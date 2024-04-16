package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.dto.response.UserListDto;

import java.util.List;

public interface AccountService {
    AccountInfoResponseDto createNewAccount (AccountRequestDto accountRequestDto);
    List<AccountInfoResponseDto> getAccountByEmailOrName (String emailOrEmail);

    AccountInfoResponseDto getAccountInfo(String token);
    UserListDto getUserList(int page, int size,String token);

    AccountInfoResponseDto addRoleToUser(String token,List<String> rolesId);
}
