package andrianianafn.gmi_api.dto.response;

import andrianianafn.gmi_api.enums.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryResponseDto {
    private String historyId;
    private MovementType movementType;
    private AccountInfoResponseDto accountDoing;
    private AccountInfoResponseDto accountAffected;
    private AccountInfoResponseDto lastAccount;
}
