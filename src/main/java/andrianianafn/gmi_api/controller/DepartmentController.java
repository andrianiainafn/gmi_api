package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.response.DepartmentResponseDto;
import andrianianafn.gmi_api.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin (origins = "*")
public class DepartmentController {
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getDepartments(){
        return new ResponseEntity<>(departmentService.getDepartments(), HttpStatus.OK);
    }
    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> addAccountToDepartment(@PathVariable String departmentId, @RequestBody List<String> userId){
        return new ResponseEntity<>(departmentService.addAccountToDepartment(departmentId, userId),HttpStatus.OK);
    }
}
