package andrianianafn.gmi_api.entity;

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

    @ManyToOne
    private Account account;
    @ManyToOne
    private Material material;
    @ManyToOne
    private Department lastDepartment;
    @ManyToOne
    private Department newDepartment;

    private Date mouvementDate;
    private Date updatedAt;

}
