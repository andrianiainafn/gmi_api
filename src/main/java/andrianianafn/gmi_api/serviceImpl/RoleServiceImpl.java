package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Organization;
import andrianianafn.gmi_api.entity.Role;
import andrianianafn.gmi_api.repository.OrganizationRepository;
import andrianianafn.gmi_api.repository.RoleRepository;
import andrianianafn.gmi_api.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private  final OrganizationRepository organizationRepository;

    public RoleServiceImpl(RoleRepository roleRepository, OrganizationRepository organizationRepository) {
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRoleOfOrganization(String organizationId) {
        return roleRepository.findAllByOrganization_OrganizationId(organizationId);
    }

    @Override
    public Role createRole(String organizationId,String roleName) {
        Organization organization = organizationRepository.findById(organizationId).orElse(null);
        Role role = Role.builder()
                .organization(organization)
                .roleName(roleName)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToOrganization(String organizationId) {
        Organization organization  = organizationRepository.findById(organizationId).orElse(null);
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role->
                role.setOrganization(organization)
        );
    }
}
