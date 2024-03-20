package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.entity.Role;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.DepartmentRepository;
import andrianianafn.gmi_api.repository.RoleRepository;
import andrianianafn.gmi_api.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createNewAccount(AccountRequestDto accountRequestDto) {
        Department department = departmentRepository.findById(accountRequestDto.getDepartmentId()).orElse(null);
        List<Role> roles =  roleRepository.findAllByRoleIdIsIn(accountRequestDto.getRolesId());
        Account account = Account.builder()
                .createdAt(new Date())
                .email(accountRequestDto.getEmail())
                .firstname(accountRequestDto.getFirstname())
                .lastname(accountRequestDto.getLastname())
                .password(passwordEncoder.encode(accountRequestDto.getPassword()))
                .roles(roles)
                .department(department)
                .build();
         Account accountSaved =  accountRepository.save(account);
         roles.stream().map(role -> {
             return role.getAccount().add(accountSaved);
         });
    }
}
