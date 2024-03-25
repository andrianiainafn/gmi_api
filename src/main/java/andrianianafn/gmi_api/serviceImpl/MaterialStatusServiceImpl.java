package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.MaterialStatus;
import andrianianafn.gmi_api.repository.MaterialStatusRepository;
import andrianianafn.gmi_api.service.MaterialStatusService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MaterialStatusServiceImpl implements MaterialStatusService {
    private final MaterialStatusRepository materialStatusRepository;

    public MaterialStatusServiceImpl(MaterialStatusRepository materialStatusRepository) {
        this.materialStatusRepository = materialStatusRepository;
    }

    @Override
    public List<MaterialStatus> getMaterialStatuses() {
        return materialStatusRepository.findAll();
    }
}
