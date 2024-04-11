package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.repository.RequestRepository;
import andrianianafn.gmi_api.service.RequestService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Request> getRequestList(String priority, int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Request> requestPage = null;
        if(priority.equals("All")){
            requestPage = requestRepository.findAllByOrderByCreatedAt(pageRequest);
        }else {
            requestPage = requestRepository.findAllByPriority_PriorityDesignationOrderByCreatedAt(priority,pageRequest);
        }
        return requestPage.getContent();
    }
}
