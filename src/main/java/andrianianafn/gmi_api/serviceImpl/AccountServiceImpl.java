package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.AccountRequestDto;
import andrianianafn.gmi_api.dto.request.CreateAccountRequest;
import andrianianafn.gmi_api.dto.request.EditProfileDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Override
    public AccountInfoResponseDto editAccountInfo(String userId, AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findById(userId).orElse(null);
        Department department = departmentRepository.findById(accountRequestDto.getDepartmentId()).orElse(null);
        List<Role> roles = roleRepository.findAllById(accountRequestDto.getRolesId());;
        if(account!=null){
            account.setRoles(roles);
            account.setDepartment(department);
            account.setEmail(accountRequestDto.getEmail());
            account.setLastname(accountRequestDto.getLastname());
            account.setFirstname(accountRequestDto.getFirstname());
            accountRepository.save(account);
        }

        return AccountInfoResponseDto.fromAccount(account);


    }

    @Override
    public AccountInfoResponseDto editProfile(String token, EditProfileDto editProfileDto) throws IOException {
        MultipartFile file = editProfileDto.getFile();
    String fileName = file.getOriginalFilename();
        String uploadPath = "uploads/" + fileName;
        Path path = Paths.get("src/main/resources/static/uploads/");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(uploadPath)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        if(account != null) {
            account.setFirstname(editProfileDto.getFirstname());
            account.setLastname(editProfileDto.getLastname());
            account.setEmail(editProfileDto.getEmail());
            account.setProfileUrl(uploadPath);
        }
        return AccountInfoResponseDto.fromAccount(account);
    }

    @Override
    public AccountInfoResponseDto signin(CreateAccountRequest createAccountRequest) {
//        Role role = Role.builder()
//                .roleName("Admin")
//                .account(new ArrayList<>())
//                .build();
//        Role roleSaved = roleRepository.save(role);
//        Account account = Account.builder()
//                .createdAt(new Date())
//                .email(createAccountRequest.email())
//                .firstname(createAccountRequest.firstname())
//                .lastname(createAccountRequest.lastname())
//                .lastname(createAccountRequest.lastname())
//                .roles(new ArrayList<>())
//                .materialsCreated(new ArrayList<>())
//                .password(passwordEncoder.encode(createAccountRequest.password()))
//                .build();
//        Account accountSaved = accountRepository.save(account);
//        accountSaved.getRoles().add(roleSaved);
//        Organization organization = Organization.builder()
//                .updatedAt(new Date())
//                .organizationLogo("")
//                .organizationOwner(accountSaved)
//                .roles(new ArrayList<>())
//                .organizationName(createAccountRequest.organizationName())
//                .createdAt(new Date())
//                .build();
//        Organization organizationSaved = organizationRepository.save(organization);
//        organizationSaved.getRoles().add(roleSaved);
//        role.getAccount().add(account);
//        role.setOrganization(organizationSaved);
        return null;
    }
}
