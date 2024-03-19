package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.LoginDto;
import andrianianafn.gmi_api.dto.request.ProviderRequestDto;
import andrianianafn.gmi_api.dto.response.AuthResponseDto;
import andrianianafn.gmi_api.dto.response.ProviderResponseDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.exceptions.RessourceNotFoundException;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    JwtEncoder jwtEncoder;
    JwtDecoder jwtDecoder;
    private final AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    @Override
    public String decodeToken(String token) {
        Jwt decodeJwt = jwtDecoder.decode(token);
        return decodeJwt.getId();
    }

    @Override
    public String generateRefreshToken(String scope, Instant instant, String subject) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .issuer("health-security")
                .expiresAt(instant.plus(15  , ChronoUnit.DAYS))
                .claim("scope",scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Override
    public String generateToken(String scope, String subject, Instant instant, boolean withRefreshToken, String accountId) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .id(accountId)
                .issuedAt(instant)
                .issuer("gmi-app")
                .expiresAt(instant.plus(withRefreshToken ? 10 : 15 , ChronoUnit.DAYS))
                .claim("scope",scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Override
    public AuthResponseDto login(LoginDto loginPassionDto) throws RessourceNotFoundException {
        String token = "";
        String accountId ="";
        String subject = "";
        Account account = null;
        if(loginPassionDto.getGrandType().equals("password")){
            System.out.println(loginPassionDto.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginPassionDto.getEmail(), loginPassionDto.getPassword())
            );
            System.out.println(authentication);
            subject = authentication.getName();
            account = accountRepository.findAccountByEmail(subject);
            accountId = account.getAccountId();


        }else if(loginPassionDto.getGrandType().equals("refreshToken")){
            Jwt decodeJwt = null;
            try {
                decodeJwt = jwtDecoder.decode(loginPassionDto.getRefreshToken());
            } catch (JwtException e) {
                throw  new RessourceNotFoundException("Token is required!");
            }
            subject = decodeJwt.getSubject();
            account = accountRepository.findAccountByEmail(subject);
            accountId = account.getAccountId();
        }
        token = this.generateToken("",subject,Instant.now(),true,accountId);
        assert account != null;
        return AuthResponseDto.builder()
                .token(token)
                .email(account.getEmail())
                .lastname(account.getLastname())
                .profileUrl(account.getProfileUrl())
                .firstname(account.getFirstname())
                .refreshToken(this.generateRefreshToken("",Instant.now(),subject))
                .build();
    }

    @Override
    public ProviderResponseDto usersProvider(ProviderRequestDto providerRequestDto) {
        Account account;
        account = accountRepository.findAccountByEmail(providerRequestDto.getEmail());
        if(account == null){
            account = Account.builder()
                    .email(providerRequestDto.getEmail())
                    .firstname(providerRequestDto.getFirstname())
                    .lastname(providerRequestDto.getLastname())
                    .profileUrl(providerRequestDto.getProfileUrl())
                    .providerType(providerRequestDto.getProviderType())
                    .build();
            Account accountSaved = accountRepository.save(account);
            return  ProviderResponseDto.builder()
                    .token(this.generateToken("",accountSaved.getEmail(),Instant.now(),true,accountSaved.getAccountId()))
                    .refreshToken(this.generateRefreshToken("",Instant.now(),accountSaved.getEmail()))
                    .build();
        }
        return  ProviderResponseDto.builder()
                .token(this.generateToken("",account.getEmail(),Instant.now(),true,account.getAccountId()))
                .refreshToken(this.generateRefreshToken("",Instant.now(),account.getEmail()))
                .build();
    }
}
