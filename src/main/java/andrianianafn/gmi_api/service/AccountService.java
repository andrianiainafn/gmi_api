package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.request.EditProfileDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.dto.response.ProfileResponseDto;
import andrianianafn.gmi_api.dto.response.UserListDto;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    AccountInfoResponseDto createNewAccount (AccountRequestDto accountRequestDto);
    List<AccountInfoResponseDto> getAccountByEmailOrName (String emailOrEmail);

    AccountInfoResponseDto getAccountInfo(String token);
    UserListDto getUserList(int page, int size,String token);

    AccountInfoResponseDto addRoleToUser(String token,List<String> rolesId);

    ProfileResponseDto getProfile(String token,int size,int page);

    AccountInfoResponseDto editAccountInfo(String userId, AccountRequestDto accountRequestDto);

    AccountInfoResponseDto editProfile(String token, EditProfileDto editProfileDto) throws IOException;
}
