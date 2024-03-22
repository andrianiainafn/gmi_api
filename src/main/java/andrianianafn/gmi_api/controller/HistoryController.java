package andrianianafn.gmi_api.controller;

import andrianianafn.gmi_api.dto.request.HistoryRequestDto;
import andrianianafn.gmi_api.dto.response.HistoryResponseDto;
import andrianianafn.gmi_api.entity.History;
import andrianianafn.gmi_api.service.HistoryService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@CrossOrigin("*")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping
    private ResponseEntity<HistoryResponseDto> creatNewMovement(@RequestBody HistoryRequestDto historyRequestDto, @RequestHeader (name = HttpHeaders.AUTHORIZATION) String authorizationHeader){
        System.out.println("fa tonga to ve alony ee");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(historyService.createNewMovement(historyRequestDto,token),HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
