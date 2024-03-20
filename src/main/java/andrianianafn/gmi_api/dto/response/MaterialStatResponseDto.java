package andrianianafn.gmi_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MaterialStatResponseDto {
    private List<MaterialStatDto> materialStats;
    private Long materialNumber;
}
