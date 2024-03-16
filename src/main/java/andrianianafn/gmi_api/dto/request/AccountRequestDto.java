package andrianianafn.gmi_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<String> rolesId;
    private String departmentId;
}
