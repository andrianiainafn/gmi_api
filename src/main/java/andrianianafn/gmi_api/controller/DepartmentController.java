package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.DepartmentRequestDto;
import andrianianafn.gmi_api.dto.response.DepartmentResponseDto;
import andrianianafn.gmi_api.service.DepartmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<List<DepartmentResponseDto>> getDepartments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(departmentService.getDepartments(token,page,size),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> addAccountToDepartment(@PathVariable String departmentId, @RequestBody List<String> userId){
        return new ResponseEntity<>(departmentService.addAccountToDepartment(departmentId, userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestBody DepartmentRequestDto departmentRequestDto ){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(departmentService.createDepartment(token,departmentRequestDto),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
