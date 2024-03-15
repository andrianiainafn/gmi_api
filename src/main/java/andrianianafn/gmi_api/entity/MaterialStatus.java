package andrianianafn.gmi_api.entity;

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
public class MaterialStatus {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String materialStatusId;
    private String materialStatusName;

    @JsonIgnore
    @OneToMany
    @Cascade (CascadeType.ALL)
    private List<Material> materials;

    private Date createdAt;
    private Date updatedAt;
}
