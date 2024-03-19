package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.repository.RequestRepository;
import andrianianafn.gmi_api.service.RequestService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public RequestStatDto getRequestStat() {
        return requestRepository.getRequestStat();
    }
}
