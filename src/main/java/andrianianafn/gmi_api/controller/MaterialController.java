package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.service.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<String> createNewMaterial(@RequestBody MaterialRequestDto materialRequestDto){
        materialService.createNewMaterial(materialRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getMaterialList(@RequestParam String status){
        return new ResponseEntity<>(materialService.getMaterialList(status),HttpStatus.OK);
    }
}
