package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.LoginDto;
import andrianianafn.gmi_api.dto.response.AuthResponseDto;
import andrianianafn.gmi_api.exceptions.RessourceNotFoundException;

import java.time.Instant;

public interface AuthService {
    String decodeToken(String token);
    String generateRefreshToken(String scope, Instant instant, String subject);
    String generateToken(String scope, String subject, Instant instant, boolean withRefreshToken, String accountId);
    AuthResponseDto loginPassion(LoginDto loginPassionDto) throws RessourceNotFoundException;
}
