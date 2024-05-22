package andrianianafn.gmi_api.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class CreateAccountRequest {
    private  String firstname;
    private  String lastname;
    private  String email;
    private  String password;
    private  String organizationName;
    private String providerType;
}
