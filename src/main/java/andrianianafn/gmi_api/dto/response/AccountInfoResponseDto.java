package andrianianafn.gmi_api.dto.response;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountInfoResponseDto {
    private String accountId;
    private String firstname;
    private String lastname;
    private String email;
    private String profileUrl;
    private Department department;

    public static AccountInfoResponseDto fromAccount(Account account){
        return AccountInfoResponseDto.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .lastname(account.getLastname())
                .profileUrl(account.getProfileUrl())
                .firstname(account.getFirstname())
                .department(account.getDepartment())
                .build();
    }
}
