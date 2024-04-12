package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.RequestRequestDto;
import andrianianafn.gmi_api.dto.response.RequestResponseDto;
import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.entity.RequestPriority;
import andrianianafn.gmi_api.enums.RequestStatus;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.PriorityRepository;
import andrianianafn.gmi_api.repository.RequestRepository;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.RequestService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final PriorityRepository priorityRepository;
    private final AuthService authService;
    private final AccountRepository accountRepository;

    public RequestServiceImpl(RequestRepository requestRepository, PriorityRepository priorityRepository, AuthService authService, AccountRepository accountRepository) {
        this.requestRepository = requestRepository;
        this.priorityRepository = priorityRepository;
        this.authService = authService;
        this.accountRepository = accountRepository;
    }

    @Override
    public RequestStatDto getRequestStat() {
        return requestRepository.getRequestStat();
    }

    @Override
    public List<RequestResponseDto> getRequestList(String priority, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Request> requestPage = null;
        if(priority.equals("All")){
            requestPage = requestRepository.findAllByOrderByCreatedAtDesc(pageRequest);
        }else {
            requestPage = requestRepository.findAllByActualPriorityOrderByCreatedAtDesc(priority,pageRequest);
        }
        return requestPage.getContent().stream().map(RequestResponseDto::fromRequest).collect(Collectors.toList());
    }

    @Override
    public Request createNewRequest(RequestRequestDto requestRequestDto,String token) {
        RequestPriority priority = priorityRepository.findById(requestRequestDto.getPriorityId()).orElse(null);
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        Request request = Request.builder()
                .actualPriority(priority.getPriorityDesignation())
                .materialName(requestRequestDto.getMaterialName())
                .description(requestRequestDto.getDescription())
                .account(account)
                .requestStatus(RequestStatus.PENDING)
                .updatedAt(new Date())
                .createdAt(new Date())
                .build();
        return  requestRepository.save(request);
    }

    @Override
    public RequestResponseDto editRequestStatus(String requestId,RequestStatus requestStatus) {
        Request request = requestRepository.findById(requestId).orElse(null);
        request.setRequestStatus(requestStatus);
        return RequestResponseDto.fromRequest(requestRepository.save(request));
    }
}
