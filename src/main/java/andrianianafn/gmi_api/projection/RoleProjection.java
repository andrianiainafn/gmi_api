package andrianianafn.gmi_api.projection;

import andrianianafn.gmi_api.entity.Role;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Role.class},name = "pr")
public interface RoleProjection {
    String getRoleId();
    String getRoleName();
}
