package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.DepartmentRequestDto;
import andrianianafn.gmi_api.dto.response.DepartmentResponseDto;
import andrianianafn.gmi_api.entity.Department;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDto> getDepartments(String token,int page ,int size);

    DepartmentResponseDto addAccountToDepartment(String departmentId, List<String> userId);

    DepartmentResponseDto createDepartment(String token, DepartmentRequestDto departmentRequestDto);
}
