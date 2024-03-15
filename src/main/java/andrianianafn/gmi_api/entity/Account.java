package andrianianafn.gmi_api.entity;

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
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;
    private String firstname;
    private String lastname;
    private String email;
    private String profileUrl;
    private String password;
    private String providerType;

    @OneToMany
    @Cascade (CascadeType.ALL)
    private List<Role> roles;

    @OneToMany
    @Cascade (CascadeType.ALL)
    private List<Request> requests;
    @ManyToOne
    private Department department;

    @ManyToMany
    private List<Notification> notifications;

    private Date createdAt;
    private Date updatedAt;
}
