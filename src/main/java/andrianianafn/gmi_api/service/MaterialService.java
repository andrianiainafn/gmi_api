package andrianianafn.gmi_api.service;

import andrianianafn.gmi_api.dto.request.EditMaterialRequestDto;
import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatResponseDto;
import andrianianafn.gmi_api.entity.Material;

import java.util.List;

public interface MaterialService {
    Material createNewMaterial(MaterialRequestDto materialRequestDto);

    List<Material> getMaterialList(String status,int page,int size);

    MaterialStatResponseDto getMaterialStat();

    Material editMaterial(EditMaterialRequestDto editMaterialRequestDto,String materialId);

    Long getTotalPage();
}
