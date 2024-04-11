package andrianianafn.gmi_api.entity;

import andrianianafn.gmi_api.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String requestId;
    private String description;
    private String materialName;
    private String actualStatus;
    private RequestStatus requestStatus;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Priority priority;

    private Date createdAt;
    private Date updatedAt;

}
