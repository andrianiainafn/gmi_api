package andrianianafn.gmi_api.dto;


import andrianianafn.gmi_api.dto.response.AccountInfoResponseDto;
import andrianianafn.gmi_api.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrganizationResponseDto {
    private String organizationId;
    private String organizationName;
    private String organizationLogo;
    private AccountInfoResponseDto accountOwner;

    public static OrganizationResponseDto fromOrganization(Organization organization){
        return OrganizationResponseDto.builder()
                .accountOwner(AccountInfoResponseDto.fromAccount(organization.getOrganizationOwner()))
                .organizationId(organization.getOrganizationId())
                .organizationLogo(organization.getOrganizationLogo())
                .organizationName(organization.getOrganizationName())
                .build();
    }
}
