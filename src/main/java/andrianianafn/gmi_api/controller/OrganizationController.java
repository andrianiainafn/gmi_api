package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.OrganizationResponseDto;
import andrianianafn.gmi_api.dto.request.DepartmentOrganizationDto;
import andrianianafn.gmi_api.dto.request.OrganizationRequestDto;
import andrianianafn.gmi_api.entity.Organization;
import andrianianafn.gmi_api.service.OrganizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("")
    public ResponseEntity<OrganizationResponseDto> createOrganization(@RequestBody OrganizationRequestDto organizationRequestDto,@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(organizationService.createOrganization(token,organizationRequestDto.getOrganizationName()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{organization-id}")
    public ResponseEntity<OrganizationResponseDto> addDepartmentToOrganization(@PathVariable ("organization-id") String organizationId,@RequestBody DepartmentOrganizationDto departmentOrganizationDto){
        return new ResponseEntity<>(organizationService.addDepartmentToOrganization(organizationId,departmentOrganizationDto.getDepartmentIds()),HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrganizationResponseDto>> getOrganizations(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(organizationService.getOrganizationList(token), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/name/{organization-id}")
    public ResponseEntity<OrganizationResponseDto> editOrganizationName(@RequestBody OrganizationRequestDto organizationRequestDto, @PathVariable ("organization-id") String organizationId){
        return new ResponseEntity<>(organizationService.editOrganizationName(organizationRequestDto.getOrganizationName(),organizationId), HttpStatus.OK);
    }
}
