package andrianianafn.gmi_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestStatDto {
    private Long approved;
    private Long pending;
    private Long rejected;
}
