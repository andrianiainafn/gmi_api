package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.RoleRequestDto;
import andrianianafn.gmi_api.entity.Role;
import andrianianafn.gmi_api.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{organization-id}")
    public ResponseEntity<List<Role>> getRoleOfOrganization(@PathVariable ("organization-id") String organizationId){
        return  new ResponseEntity<>(roleService.getRoleOfOrganization(organizationId), HttpStatus.OK);
    }

    @PostMapping("/{organization-id}")
    public ResponseEntity<Role> createNewRole(@PathVariable ("organization-id") String organizationId,@RequestBody RoleRequestDto roleRequestDto) {
        return new ResponseEntity<>(roleService.createRole(organizationId, roleRequestDto.getRoleName()),HttpStatus.CREATED);
    }

    @PutMapping("/{organization-id}")
    public ResponseEntity<String> addRoleToOrganization(@PathVariable ("organization-id") String organizationId){
        roleService.addRoleToOrganization(organizationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
