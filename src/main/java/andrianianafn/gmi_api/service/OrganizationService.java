package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.OrganizationResponseDto;
import andrianianafn.gmi_api.entity.Organization;

import java.util.List;

public interface OrganizationService {
    OrganizationResponseDto createOrganization(String token,String organizationName);

    OrganizationResponseDto addDepartmentToOrganization(String organizationId,List<String> departmentIds);

    List<OrganizationResponseDto> getOrganizationList(String token);
}
