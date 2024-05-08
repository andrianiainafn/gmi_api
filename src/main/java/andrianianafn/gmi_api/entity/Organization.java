package andrianianafn.gmi_api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String organizationId;
    private String organizationName;
    private String organizationLogo;

    @ManyToOne
    private Account organizationOwner;
    @OneToMany
    private List<Role> roles;
    @OneToMany
    private List<Department> departments;

    private Date createdAt;
    private Date updatedAt;
}
