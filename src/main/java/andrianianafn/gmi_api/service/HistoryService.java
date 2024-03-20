package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.HistoryRequestDto;
import andrianianafn.gmi_api.entity.History;

public interface HistoryService {
    History createNewMovement(HistoryRequestDto historyRequestDto,String token);
}
