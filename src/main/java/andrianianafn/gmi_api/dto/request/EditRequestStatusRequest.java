package andrianianafn.gmi_api.dto.request;

import andrianianafn.gmi_api.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EditRequestStatusRequest {
    private RequestStatus requestStatus;
}
