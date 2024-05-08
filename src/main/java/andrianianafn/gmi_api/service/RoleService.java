package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.entity.Role;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface RoleService {
    List<Role> getRoleList();

    List<Role> getRoleOfOrganization(String organizationId);

    Role createRole(String organizationId,String roleName);

    void addRoleToOrganization(String organizationId);
}
