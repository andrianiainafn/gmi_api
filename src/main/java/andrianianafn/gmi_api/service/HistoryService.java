package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.HistoryRequestDto;
import andrianianafn.gmi_api.dto.response.HistoryResponseDto;

public interface HistoryService {
    HistoryResponseDto createNewMovement(HistoryRequestDto historyRequestDto, String token);
}
