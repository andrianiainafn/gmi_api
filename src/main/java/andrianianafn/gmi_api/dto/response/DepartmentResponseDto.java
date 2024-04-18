package andrianianafn.gmi_api.dto.response;


import andrianianafn.gmi_api.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentResponseDto {
    private List<AccountDepartmentDto> account;
    private String departmentId;
    private String departmentName;
    private Date createdAt;
    private Date updatedAt;

    public static DepartmentResponseDto fromDepartment(Department department){
       DepartmentResponseDto departmentResponseDto = DepartmentResponseDto.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
       List<AccountDepartmentDto> accountDepartmentDto = department.getAccounts().stream().map(AccountDepartmentDto::fromAccount).toList();
       departmentResponseDto.setAccount(accountDepartmentDto);
        return departmentResponseDto;
    }
}
