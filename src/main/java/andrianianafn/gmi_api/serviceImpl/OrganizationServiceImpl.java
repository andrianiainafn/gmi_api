package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.OrganizationResponseDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.entity.Organization;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.DepartmentRepository;
import andrianianafn.gmi_api.repository.OrganizationRepository;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final AuthService authService;
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, AuthService authService, AccountRepository accountRepository, DepartmentRepository departmentRepository) {
        this.organizationRepository = organizationRepository;
        this.authService = authService;
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public OrganizationResponseDto createOrganization(String token, String organizationName) {
        Account owner = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        Organization organization = Organization.builder()
                .organizationName(organizationName)
                .organizationLogo("")
                .organizationOwner(owner)
                .departments(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        Organization organizationSave = organizationRepository.save(organization);
        if(owner != null){
            if(owner.getOrganizations() != null){
                owner.getOrganizations().add(organization);
            }else{
                List<Organization> organizations = new ArrayList<>();
                organizations.add(organizationSave);
                owner.setOrganizations(organizations);
            }
        }
        return OrganizationResponseDto.fromOrganization(organizationSave);
    }

    @Override
    public OrganizationResponseDto addDepartmentToOrganization(String organizationId, List<String> departmentIds) {
        List<Department> departments = departmentRepository.findAllById(departmentIds);
        Organization organization = organizationRepository.findById(organizationId).orElse(null);
        if (organization!=null) {
            organization.getDepartments().addAll(departments);
            organizationRepository.save(organization);
        }
        departments.forEach(department -> {
            department.setOrganization(organization);
            departmentRepository.save(department);
        });
        assert organization != null;
        return OrganizationResponseDto.fromOrganization(organization);
    }

    @Override
    public List<OrganizationResponseDto> getOrganizationList(String token) {
        List<Organization> organizations = organizationRepository.findAllByOrganizationOwner_AccountId(authService.decodeToken(token));
        return organizations.stream().map(OrganizationResponseDto::fromOrganization).collect(Collectors.toList());
    }

    @Override
    public OrganizationResponseDto editOrganizationName(String organizationName, String organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElse(null);
        if(organization != null) {
            organization.setOrganizationName(organizationName);
            return OrganizationResponseDto.fromOrganization(organization);
        }
        return null;
    }


}
