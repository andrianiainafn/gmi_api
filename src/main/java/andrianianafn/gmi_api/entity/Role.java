package andrianianafn.gmi_api.entity;

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
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String roleId;
    private String roleName;

    @ManyToOne
    private Account account;

    private Date createdAt;
    private Date updatedAt;
}
