package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrganizationRepository extends JpaRepository<Organization,String> {

    List<Organization> findAllByOrganizationOwner_AccountId(String organizationOwner_accountId);

}
