package andrianianafn.gmi_api.entity;

import andrianianafn.gmi_api.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MaintenanceRequest {
    @Id @GeneratedValue (strategy= GenerationType.UUID)
    private String maintenanceRequestId;
    private String description;
    private RequestStatus requestStatus;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Material> material;

    private Date createdAt;
    private Date updatedAt;
}
