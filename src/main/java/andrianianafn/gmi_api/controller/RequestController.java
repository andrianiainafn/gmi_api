package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.EditRequestStatusRequest;
import andrianianafn.gmi_api.dto.request.RequestRequestDto;
import andrianianafn.gmi_api.dto.response.RequestResponseDto;
import andrianianafn.gmi_api.dto.response.RequestStatDto;
import andrianianafn.gmi_api.entity.Request;
import andrianianafn.gmi_api.enums.RequestStatus;
import andrianianafn.gmi_api.service.RequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<List<RequestResponseDto>> getRequestList(@RequestParam("priority") String priority, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return new ResponseEntity<>(requestService.getRequestList(priority, page, size),HttpStatus.OK);
    }

    @PostMapping("")
    public  ResponseEntity<Request> createRequest(@RequestBody RequestRequestDto requestRequestDto,@RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        System.out.println("fa tonga to ve alony ee");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(requestService.createNewRequest(requestRequestDto,token),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/{request-id}")
    public ResponseEntity<RequestResponseDto> editRequestStatus(@PathVariable ("request-id") String requestId, @RequestBody EditRequestStatusRequest requestStatus){
        System.out.println(requestStatus);
        return new ResponseEntity<>(requestService.editRequestStatus(requestId,requestStatus.getRequestStatus()),HttpStatus.OK);
    }
}
