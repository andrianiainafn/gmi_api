package andrianianafn.gmi_api.projection;

import andrianianafn.gmi_api.entity.Department;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Department.class},name = "p1")
public interface DepartmentProjection {
    String getDepartmentId();
    String getDepartmentName();
}
