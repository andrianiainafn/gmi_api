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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId;
    private boolean isRead;
    private String notificationType;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Department newDepartment;
    @ManyToOne
    private Department lastDepartment;

    @ManyToMany
    @JsonIgnore
    private List<Account> concernedAccounts;

    private Date createdAt;
    private Date updatedAt;
}
