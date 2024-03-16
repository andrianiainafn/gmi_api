package andrianianafn.gmi_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {
    private String firstname;
    private String lastname;
    private String email;
    private String profileUrl;
    private String token;
    private String refreshToken;
}
