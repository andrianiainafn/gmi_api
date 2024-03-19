package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatDto;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.entity.MaterialStatus;
import andrianianafn.gmi_api.repository.MaterialRepository;
import andrianianafn.gmi_api.repository.MaterialStatusRepository;
import andrianianafn.gmi_api.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialStatusRepository materialStatusRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialStatusRepository materialStatusRepository) {
        this.materialRepository = materialRepository;
        this.materialStatusRepository = materialStatusRepository;
    }

    @Override
    public void createNewMaterial(MaterialRequestDto materialRequestDto) {
        MaterialStatus materialStatus = materialStatusRepository.getReferenceById(materialRequestDto.getStatusId());
        Material material = Material.builder()
                .materialName(materialRequestDto.getMaterialName())
                .description(materialRequestDto.getDescription())
                .serialNumber(materialRequestDto.getSerialNumber())
                .state(materialRequestDto.getState())
                .actualStatus(materialStatus.getMaterialStatusName())
                .materialStatus(materialStatus)
                .build();
        materialRepository.save(material);
    }

    @Override
    public List<Material> getMaterialList(String status) {
        return materialRepository.findByActualStatus(status);
    }

    @Override
    public List<MaterialStatDto> getMaterialStatList() {
        return materialRepository.findStatMaterial();
    }
}
