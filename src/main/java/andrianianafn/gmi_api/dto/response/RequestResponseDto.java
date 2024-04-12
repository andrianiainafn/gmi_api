package andrianianafn.gmi_api.dto.response;

import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestResponseDto {
    private String requestId;
    private String description;
    private String materialName;
    private String actualPriority;
    private RequestStatus requestStatus;
    private AccountInfoResponseDto account;
    private Date createdAt;

    public  static RequestResponseDto fromRequest(Request request){
        AccountInfoResponseDto accountInfoResponseDto = AccountInfoResponseDto.builder()
                .accountId(request.getAccount().getAccountId())
                .email(request.getAccount().getEmail())
                .firstname(request.getAccount().getFirstname())
                .lastname(request.getAccount().getLastname())
                .roles(request.getAccount().getRoles())
                .department(request.getAccount().getDepartment())
                .build();
        return RequestResponseDto.builder()
                .requestId(request.getRequestId())
                .description(request.getDescription())
                .materialName(request.getMaterialName())
                .actualPriority(request.getActualPriority())
                .requestStatus(request.getRequestStatus())
                .account(accountInfoResponseDto)
                .createdAt(request.getCreatedAt())
                .build();
    }
}
