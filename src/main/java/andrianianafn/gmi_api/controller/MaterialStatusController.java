package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.entity.MaterialStatus;
import andrianianafn.gmi_api.service.MaterialStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/status")
@CrossOrigin("*")
public class MaterialStatusController {
    private final MaterialStatusService materialStatusService;

    public MaterialStatusController(MaterialStatusService materialStatusService) {
        this.materialStatusService = materialStatusService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialStatus>> getMaterialStatusList(){
        return new ResponseEntity<>(materialStatusService.getMaterialStatuses(),HttpStatus.OK);
    }
}
