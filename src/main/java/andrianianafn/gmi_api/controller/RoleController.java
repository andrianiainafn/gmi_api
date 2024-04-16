package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.entity.Role;
import andrianianafn.gmi_api.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "*")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> geRoleList(){
        return new ResponseEntity<>(roleService.getRoleList(), HttpStatus.OK);
    }
}
