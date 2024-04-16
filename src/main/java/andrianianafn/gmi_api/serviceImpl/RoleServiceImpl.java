package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Role;
import andrianianafn.gmi_api.repository.RoleRepository;
import andrianianafn.gmi_api.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }
}
