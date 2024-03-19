package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatDto;
import andrianianafn.gmi_api.entity.Material;

import java.util.List;

public interface MaterialService {
    void createNewMaterial(MaterialRequestDto materialRequestDto);

    List<Material> getMaterialList(String status);

    List<MaterialStatDto> getMaterialStatList();
}
