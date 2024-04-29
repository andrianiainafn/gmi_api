package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.dto.response.ProfileResponseDto;
import andrianianafn.gmi_api.dto.response.UserListDto;
import andrianianafn.gmi_api.entity.*;
import andrianianafn.gmi_api.repository.*;
import andrianianafn.gmi_api.service.AccountService;
import andrianianafn.gmi_api.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RequestRepository requestRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final OrganizationRepository organizationRepository;

    public AccountServiceImpl(AccountRepository accountRepository, RequestRepository requestRepository, DepartmentRepository departmentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthService authService, OrganizationRepository organizationRepository) {
        this.accountRepository = accountRepository;
        this.requestRepository = requestRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public AccountInfoResponseDto createNewAccount(AccountRequestDto accountRequestDto) {
        Department department = departmentRepository.findById(accountRequestDto.getDepartmentId()).orElse(null);
        List<Role> roles = roleRepository.findAllById(accountRequestDto.getRolesId());;
        Account account = Account.builder()
                .createdAt(new Date())
                .email(accountRequestDto.getEmail())
                .firstname(accountRequestDto.getFirstname())
                .lastname(accountRequestDto.getLastname())
                .materialsCreated(new ArrayList<>())
                .password(passwordEncoder.encode(accountRequestDto.getPassword()))
                .department(department)
                .build();
        if (account.getRoles() != null) {
            account.getRoles().addAll(roles);
        }else{
            account.setRoles(roles);
        }
        Account accountSaved =  accountRepository.save(account);
        roles.forEach(role -> {
            role.getAccount().add(accountSaved);
        });
        if(department != null){
            if (!department.getAccounts().isEmpty()){
                department.getAccounts().add(account);
            }else {
                List<Account> newList = new ArrayList<>();
                newList.add(account);
                department.setAccounts(newList);
            }
        }
        assert department != null;
        departmentRepository.save(department);
         return AccountInfoResponseDto.fromAccount(accountSaved);
    }

    @Override
    public List<AccountInfoResponseDto> getAccountByEmailOrName(String emailOrEmail) {
        List<Account> account = accountRepository.findAllByEmailContainingOrFirstnameContainingOrLastnameContaining(emailOrEmail,emailOrEmail,emailOrEmail);
        return account.stream().map(AccountInfoResponseDto::fromAccount).collect(Collectors.toList());
    }

    @Override
    public AccountInfoResponseDto getAccountInfo(String token) {
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        if (account == null) {
            return null;
        }
        return AccountInfoResponseDto.fromAccount(account);
    }

    @Override
    public UserListDto getUserList(int page, int size,String token) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Department department = accountRepository.findById(authService.decodeToken(token)).orElse(null).getDepartment();
        List<Account> accounts = accountRepository.findAllByDepartment_Organization_OrganizationIdAndAccountIdIsNot(department.getOrganization().getOrganizationId(), authService.decodeToken(token),pageRequest ).getContent();
        return UserListDto.builder()
                .users(accounts.stream().map(AccountInfoResponseDto::fromAccount).collect(Collectors.toList()))
                .totalPages(accounts.size()/size)
                .build();
    }

    @Override
    public AccountInfoResponseDto addRoleToUser(String token, List<String> rolesId) {
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        List<Role> roles = roleRepository.findAllById(rolesId);
        if (account != null) {
            account.getRoles().addAll(roles);
            roles.forEach(role -> {
                role.getAccount().add(account);
            });
        }
        return AccountInfoResponseDto.fromAccount(account);
    }

    @Override
    public ProfileResponseDto getProfile(String token,int size,int page) {
        PageRequest pageRequest = PageRequest.of(page, size);
        String accountId = authService.decodeToken(token);
        Account account = accountRepository.findById(accountId).orElse(null);
        List<Request> requests = requestRepository.findAllByAccount_AccountId(accountId,pageRequest).getContent();
        return ProfileResponseDto.builder()
                .account(AccountInfoResponseDto.fromAccount(account))
                .requests(requests)
                .build();
    }
}
