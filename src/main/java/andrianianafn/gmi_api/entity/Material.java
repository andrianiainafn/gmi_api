package andrianianafn.gmi_api.entity;

import andrianianafn.gmi_api.enums.State;
import andrianianafn.gmi_api.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String materialId;
    private String materialName;
    private String serialNumber;
    private String description;
    private String actualStatus;
    private State state;

    @ManyToOne
    private MaterialStatus materialStatus;
    @OneToMany
    @Cascade (CascadeType.ALL)
    private List<History> histories;
    @ManyToOne
    @JsonIgnore
    private MaintenanceRequest maintenanceRequest;

    private Date createdAt;
    private Date updatedAt;
}
