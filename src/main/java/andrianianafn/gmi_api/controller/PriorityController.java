package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.service.PriorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import andrianianafn.gmi_api.entity.Priority;

import java.util.List;

@RestController
@RequestMapping("/api/priority")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @GetMapping("")
    public ResponseEntity<List<Priority>> getPriorityList(){
        return new ResponseEntity<>(priorityService.getPriorities(), HttpStatus.OK);
    }
}
