package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@CrossOrigin ("*")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/stat")
    public ResponseEntity<RequestStatDto> getRequestStat(){
        return  new ResponseEntity<>(requestService.getRequestStat(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Request>> getRequestList(@RequestParam("priority") String priority,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        return new ResponseEntity<>(requestService.getRequestList(priority, page, size),HttpStatus.OK);
    }
}
