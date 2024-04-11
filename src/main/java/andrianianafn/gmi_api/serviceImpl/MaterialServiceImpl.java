package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatDto;
import andrianianafn.gmi_api.dto.response.MaterialStatResponseDto;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.entity.MaterialStatus;
import andrianianafn.gmi_api.repository.MaterialRepository;
import andrianianafn.gmi_api.repository.MaterialStatusRepository;
import andrianianafn.gmi_api.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Material createNewMaterial(MaterialRequestDto materialRequestDto) {
        MaterialStatus materialStatus = materialStatusRepository.getReferenceById(materialRequestDto.getStatusId());
        Material material = Material.builder()
                .materialName(materialRequestDto.getMaterialName())
                .description(materialRequestDto.getDescription())
                .serialNumber(materialRequestDto.getSerialNumber())
                .state(materialRequestDto.getState())
                .createdAt(new Date())
                .updatedAt(new Date())
                .actualStatus(materialStatus.getMaterialStatusName())
                .materialStatus(materialStatus)
                .build();
        Material materialSaved = materialRepository.save(material);
        return  Material.builder()
                .materialId(materialSaved.getMaterialId())
                .materialName(materialSaved.getMaterialName())
                .description(materialSaved.getDescription())
                .actualStatus(materialSaved.getActualStatus())
                .serialNumber(materialSaved.getSerialNumber())
                .updatedAt(materialSaved.getUpdatedAt())
                .createdAt(materialSaved.getCreatedAt())
                .account(materialSaved.getAccount())
                .build();
    }

    @Override
    public List<Material> getMaterialList(String status,int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Material> materialPage = null;
        if(status.equals("All")){
            materialPage = materialRepository.findAllByOrderByCreatedAt(pageRequest);
        }else{
             materialPage = materialRepository.findByActualStatusOrderByCreatedAt(status,pageRequest);
        }
        return materialPage.getContent();
    }

    @Override
    public MaterialStatResponseDto getMaterialStat() {
        return MaterialStatResponseDto.builder()
                .materialNumber(materialRepository.getMaterialNumber())
                .materialStats(materialRepository.findStatMaterial())
                .build();
    }

    @Override
    public Long getTotalPage() {
        return materialRepository.count();
    }
}
