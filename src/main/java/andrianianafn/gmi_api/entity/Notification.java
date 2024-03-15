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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String notificationId;
    private boolean isRead;

    @ManyToOne
    private Account account;
    @ManyToOne
    private Department newDepartment;
    @ManyToOne
    private Department lastDepartment;

    private Date createdAt;
    private Date updatedAt;
}
