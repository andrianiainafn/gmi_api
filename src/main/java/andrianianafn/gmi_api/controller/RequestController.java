package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
