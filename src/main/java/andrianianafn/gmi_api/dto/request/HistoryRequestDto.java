package andrianianafn.gmi_api.dto.request;

import andrianianafn.gmi_api.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryRequestDto {
    private MovementType movementType;
    private String accountReceivedId;
    private String materialId;
}
