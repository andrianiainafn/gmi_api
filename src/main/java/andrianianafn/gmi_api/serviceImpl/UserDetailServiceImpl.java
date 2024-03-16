package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    public UserDetailServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account passion = accountRepository.findAccountByEmail(email);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new User(email,passion.getPassword(),true,true,true,true,authorities);
    }
}
