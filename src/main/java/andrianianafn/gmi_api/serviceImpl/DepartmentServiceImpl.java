package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.DepartmentRequestDto;
import andrianianafn.gmi_api.dto.response.DepartmentResponseDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.entity.Organization;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.DepartmentRepository;
import andrianianafn.gmi_api.repository.OrganizationRepository;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AccountRepository accountRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthService authService;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AccountRepository accountRepository, OrganizationRepository organizationRepository, AuthService authService) {
        this.departmentRepository = departmentRepository;
        this.accountRepository = accountRepository;
        this.organizationRepository = organizationRepository;
        this.authService = authService;
    }

    @Override
    public List<DepartmentResponseDto> getDepartments(String token,int page ,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        if (account.getDepartment() == null){
            return  new ArrayList<>();
        }
        List<Department> departments =  departmentRepository.findAllByOrganization_OrganizationId(account.getDepartment().getOrganization().getOrganizationId(), pageRequest).getContent();
        return departments.stream().map(DepartmentResponseDto::fromDepartment).toList();
    }

    @Override
    public DepartmentResponseDto addAccountToDepartment(String departmentId, List<String> userId) {
        List<Account> account = accountRepository.findAllById(userId);
        Department department = departmentRepository.findById(departmentId).orElse(null);
        if(department != null) {
            department.getAccounts().addAll(account);
            account.forEach(account1 -> {
                account1.setDepartment(department);
            });
            return DepartmentResponseDto.fromDepartment(department);
        }
        return null;
    }

    @Override
    public DepartmentResponseDto createDepartment(String token, DepartmentRequestDto departmentRequestDto) {
        Department departmentFind = accountRepository.findById(authService.decodeToken(token)).get().getDepartment();
        List<Department> departments = new ArrayList<>();
        departments.add(departmentFind);
        Organization organization = organizationRepository.findOrganizationsByDepartmentsContains(departments).get(0);
        List<Account> accounts = accountRepository.findAllById(departmentRequestDto.getUserId());
        Department department = Department.builder()
                .departmentName(departmentRequestDto.getDepartmentName())
                .createdAt(new Date())
                .updatedAt(new Date())
                .organization(organization)
                .build();
        Department departmentSaved = departmentRepository.save(department);
        departmentSaved.setAccounts(accounts);
        accounts.forEach(account -> {
            account.setDepartment(departmentSaved);
        });
        return DepartmentResponseDto.fromDepartment(departmentSaved);
    }
}
