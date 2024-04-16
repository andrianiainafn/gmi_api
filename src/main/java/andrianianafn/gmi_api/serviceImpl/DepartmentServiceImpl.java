package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.entity.Department;
import andrianianafn.gmi_api.repository.DepartmentRepository;
import andrianianafn.gmi_api.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }
}
