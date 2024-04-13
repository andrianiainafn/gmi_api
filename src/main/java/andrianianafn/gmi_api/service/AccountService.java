package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;

import java.util.List;

public interface AccountService {
    void createNewAccount (AccountRequestDto accountRequestDto);
    List<AccountInfoResponseDto> getAccountByEmailOrName (String emailOrEmail);
}
