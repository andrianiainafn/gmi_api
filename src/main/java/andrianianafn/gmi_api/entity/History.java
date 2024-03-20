package andrianianafn.gmi_api.entity;

import andrianianafn.gmi_api.enums.MovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class History {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String historyId;
    private MovementType movementType;

    @ManyToOne
    private Account accountDoing;
    @ManyToOne
    private Account accountAffected;
    @ManyToOne
    private Material material;
    @ManyToOne
    private Department lastDepartment;
    @ManyToOne
    private Department newDepartment;

    private Date mouvementDate;
    private Date updatedAt;

}
