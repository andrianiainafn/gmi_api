package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.EditMaterialRequestDto;
import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatResponseDto;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.service.MaterialService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<Material> createNewMaterial(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestBody MaterialRequestDto materialRequestDto){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            Material newMaterial = materialService.createNewMaterial(token,materialRequestDto);
            return new ResponseEntity<>(newMaterial,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getMaterialList(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestParam String status,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(materialService.getMaterialList(token,status,page,size),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/stat")
    public  ResponseEntity<MaterialStatResponseDto> getMaterialStat(){
        return new ResponseEntity<>(materialService.getMaterialStat(),HttpStatus.OK);
    }

    @GetMapping("/page_size")
    public ResponseEntity<Long> getPageSize(){
        return new ResponseEntity<>(materialService.getTotalPage(),HttpStatus.OK);
    }

    @PutMapping("/{material-id}")
    public ResponseEntity<Material> editMaterial(@PathVariable ("material-id") String materialId, @RequestBody EditMaterialRequestDto editMaterialRequestDto){
        return new ResponseEntity<>(materialService.editMaterial(editMaterialRequestDto,materialId),HttpStatus.OK);
    }
    @PutMapping("/correction")
    public ResponseEntity<List<Material>> addOwnerForMaterial(@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(materialService.addOwnerForMaterials(token ,page, size),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }   
}
