package andrianianafn.gmi_api.dto.request;

public record CreateAccountRequest(
        String firstname,
        String lastname,
        String email,
        String password,
        String organizationName
) {
}
