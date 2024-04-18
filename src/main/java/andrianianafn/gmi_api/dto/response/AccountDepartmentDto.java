package andrianianafn.gmi_api.dto.response;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountDepartmentDto {
    private String accountId;
    private String firstname;
    private String lastname;
    private String email;
    private String profileUrl;
    private Date createdAt;
    private List<Role> roles;

    public static AccountDepartmentDto fromAccount(Account account){
        return AccountDepartmentDto.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .lastname(account.getLastname())
                .profileUrl(account.getProfileUrl())
                .firstname(account.getFirstname())
                .createdAt(account.getCreatedAt())
                .roles(account.getRoles())
                .build();
    }
}
