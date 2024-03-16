package andrianianafn.gmi_api.dto.request;

import andrianianafn.gmi_api.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialRequestDto {
    private String materialName;
    private String serialNumber;
    private String description;
    private String statusId;
    private State state;
}
