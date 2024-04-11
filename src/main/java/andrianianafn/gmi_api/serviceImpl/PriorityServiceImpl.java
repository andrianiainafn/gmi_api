package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Priority;
import andrianianafn.gmi_api.repository.PriorityRepository;
import andrianianafn.gmi_api.service.PriorityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public List<Priority> getPriorities() {
        return priorityRepository.findAll();
    }
}
