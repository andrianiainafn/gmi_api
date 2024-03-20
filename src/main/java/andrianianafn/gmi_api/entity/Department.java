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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String departmentId;
    private String departmentName;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Account> accounts;

//    @OneToMany(mappedBy = "newDepartment")
//    @JsonIgnore
//    @Cascade(CascadeType.ALL)
//    private List<History> histories;

    private Date createdAt;
    private Date updatedAt;
}