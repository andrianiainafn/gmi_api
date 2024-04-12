package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.RequestRequestDto;
import andrianianafn.gmi_api.dto.response.RequestResponseDto;
import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.enums.RequestStatus;

import java.util.List;

public interface RequestService {
    RequestStatDto getRequestStat();

    List<RequestResponseDto> getRequestList(String priority, int page, int size);

    Request createNewRequest(RequestRequestDto requestRequestDto,String token);

    RequestResponseDto editRequestStatus(String requestId, RequestStatus requestStatus);
}
