package andrianianafn.gmi_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepartmentRequestDto {
    private List<String> userId;
    private String departmentName;
}
