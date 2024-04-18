package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.response.DepartmentResponseDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.DepartmentRepository;
import andrianianafn.gmi_api.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final AccountRepository accountRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AccountRepository accountRepository) {
        this.departmentRepository = departmentRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<DepartmentResponseDto> getDepartments() {
        List<Department> departments =  departmentRepository.findAll();
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
}
