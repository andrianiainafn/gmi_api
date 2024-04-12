package andrianianafn.gmi_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class RequestPriority {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String priorityId;
    private String priorityDesignation;

    @OneToMany
    private List<Request> request;
}
