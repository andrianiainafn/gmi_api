package andrianianafn.gmi_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String roleId;
    private String roleName;

    @JsonIgnore
    @ManyToMany
    private List<Account> account;
    @JsonIgnore
    @ManyToOne
    private Organization organization;
    private Date createdAt;
    private Date updatedAt;
}
