package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.EditMaterialRequestDto;
import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatResponseDto;
import andrianianafn.gmi_api.entity.Account;
import andrianianafn.gmi_api.entity.Material;
import andrianianafn.gmi_api.entity.MaterialStatus;
import andrianianafn.gmi_api.repository.AccountRepository;
import andrianianafn.gmi_api.repository.MaterialRepository;
import andrianianafn.gmi_api.repository.MaterialStatusRepository;
import andrianianafn.gmi_api.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialStatusRepository materialStatusRepository;

    private final AccountRepository accountRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialStatusRepository materialStatusRepository, AccountRepository accountRepository) {
        this.materialRepository = materialRepository;
        this.materialStatusRepository = materialStatusRepository;
        this.accountRepository = accountRepository;
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
                .accounts(materialSaved.getAccounts())
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
    public Material editMaterial(EditMaterialRequestDto editMaterialRequestDto,String materialId) {
        Material material = materialRepository.findById(materialId).orElse(null);
        MaterialStatus materialStatus = materialStatusRepository.findById(editMaterialRequestDto.getStatusId()).orElse(null);
        List<Account> accounts = accountRepository.findAllById(editMaterialRequestDto.getAccountId());
        if(material != null){
            material.setMaterialName(editMaterialRequestDto.getMaterialName());
            material.setMaterialStatus(materialStatus);
            material.setDescription(editMaterialRequestDto.getDescription());
            material.setSerialNumber(editMaterialRequestDto.getSerialNumber());
            if(materialStatus != null){
                material.setActualStatus(materialStatus.getMaterialStatusName());
            }
            material.setAccounts(accounts);
            materialRepository.save(material);
        }
        return  material;
    }

    @Override
    public Long getTotalPage() {
        return materialRepository.count();
    }
}
