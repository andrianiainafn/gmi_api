package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,String> {

    Account findAccountByEmail(String email);
    Account findAccountByAccountId(String accountId);

    List<Account> findAllByEmailContainingOrFirstnameContainingOrLastnameContaining(String email, String firstname, String lastname);

    Page<Account> findAllByAccountIdIsNot(String accountId, Pageable pageable);

    Page<Account> findAllByDepartment_Organization_OrganizationIdAndAccountIdIsNot(String department_organization_organizationId, String accountId, Pageable pageable);
}
