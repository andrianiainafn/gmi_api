package andrianianafn.gmi_api.dto.response;


import andrianianafn.gmi_api.entity.MaintenanceRequest;
import andrianianafn.gmi_api.entity.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfileResponseDto {
    private AccountInfoResponseDto account;
    private List<Request> requests;
    private List<MaintenanceRequest> maintenanceRequests;
}
