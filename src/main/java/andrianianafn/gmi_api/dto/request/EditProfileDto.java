package andrianianafn.gmi_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class EditProfileDto {
    private String firstname;
    private String lastname;
    private String email;
    private MultipartFile file;
}
