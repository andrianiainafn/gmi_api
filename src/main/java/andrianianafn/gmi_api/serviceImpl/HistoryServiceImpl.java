package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.HistoryRequestDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.History;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.HistoryRepository;
import andrianianafn.gmi_api.repository.MaterialRepository;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.HistoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;
    private final MaterialRepository materialRepository;
    private final AuthService authService;

    public HistoryServiceImpl(HistoryRepository historyRepository, AccountRepository accountRepository, MaterialRepository materialRepository, AuthService authService) {
        this.historyRepository = historyRepository;
        this.accountRepository = accountRepository;
        this.materialRepository = materialRepository;
        this.authService = authService;
    }

    @Override
    public History createNewMovement(HistoryRequestDto historyRequestDto,String token) {
        Material material = materialRepository.findById(historyRequestDto.getMaterialId()).orElse(null);
        Account accountReceived = accountRepository.findById(historyRequestDto.getAccountReceivedId()).orElse(null);
        Account accountDoing = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        Account lastAccount = null;
        if(material != null){
            lastAccount = material.getAccount();
        }
        History history = History.builder()
                .accountDoing(accountDoing)
                .accountAffected(accountReceived)
                .lastAccount(lastAccount)
                .material(material)
                .mouvementDate(new Date())
                .updatedAt(new Date())
                .movementType(historyRequestDto.getMovementType())
                .build();
        return historyRepository.save(history);
    }
}
