package andrianianafn.gmi_api.dto.request;

import andrianianafn.gmi_api.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EditMaterialRequestDto {
    private String materialName;
    private String serialNumber;
    private String description;
    private String statusId;
    private State state;
    private List<String> accountId;
}
