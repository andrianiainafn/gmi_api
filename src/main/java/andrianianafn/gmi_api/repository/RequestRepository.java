package andrianianafn.gmi_api.repository;

import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RequestRepository extends JpaRepository<Request,String> {

    @Query("SELECT new andrianianafn.gmi_api.dto.response.RequestStatDto("
            + "SUM(CASE WHEN r.requestStatus = andrianianafn.gmi_api.enums.RequestStatus.APPROVED THEN 1 ELSE 0 END), "
            + "SUM(CASE WHEN r.requestStatus = andrianianafn.gmi_api.enums.RequestStatus.PENDING THEN 1 ELSE 0 END), "
            + "SUM(CASE WHEN r.requestStatus = andrianianafn.gmi_api.enums.RequestStatus.REJECTED THEN 1 ELSE 0 END))"
            + " FROM Request r")
    RequestStatDto getRequestStat();

    Page<Request> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Request> findAllByActualPriorityOrderByCreatedAtDesc(String priorityDesignation,Pageable pageable);

    Page<Request> findAllByAccount_AccountId(String account_accountId, Pageable pageable);
}
