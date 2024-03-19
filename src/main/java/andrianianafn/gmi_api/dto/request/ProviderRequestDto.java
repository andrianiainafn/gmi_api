package andrianianafn.gmi_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProviderRequestDto {
    private String firstname;
    private String email;
    private String lastname;
    private String profileUrl;
    private String providerType;
}
