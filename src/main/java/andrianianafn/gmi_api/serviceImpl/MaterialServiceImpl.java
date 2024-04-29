package andrianianafn.gmi_api.serviceImpl;

import andrianianafn.gmi_api.dto.request.EditMaterialRequestDto;
import andrianianafn.gmi_api.dto.request.MaterialRequestDto;
import andrianianafn.gmi_api.dto.response.MaterialStatResponseDto;
import andrianianafn.gmi_api.entity.*;
import andrianianafn.gmi_api.repository.*;
import andrianianafn.gmi_api.service.AuthService;
import andrianianafn.gmi_api.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository materialRepository;
    private final MaterialStatusRepository materialStatusRepository;

    private final AccountRepository accountRepository;
    private final DepartmentRepository departmentRepository;
    private final AuthService authService;
    private final OrganizationRepository organizationRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, MaterialStatusRepository materialStatusRepository, AccountRepository accountRepository, DepartmentRepository departmentRepository, AuthService authService, OrganizationRepository organizationRepository) {
        this.materialRepository = materialRepository;
        this.materialStatusRepository = materialStatusRepository;
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.authService = authService;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Material createNewMaterial(String token,MaterialRequestDto materialRequestDto) {
        Account owner = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        MaterialStatus materialStatus = materialStatusRepository.getReferenceById(materialRequestDto.getStatusId());
        Material material = Material.builder()
                .materialName(materialRequestDto.getMaterialName())
                .description(materialRequestDto.getDescription())
                .serialNumber(materialRequestDto.getSerialNumber())
                .state(materialRequestDto.getState())
                .createdAt(new Date())
                .updatedAt(new Date())
                .owner(owner)
                .accounts(new ArrayList<>())
                .actualStatus(materialStatus.getMaterialStatusName())
                .materialStatus(materialStatus)
                .build();
        Material materialSaved = materialRepository.save(material);
        if (owner!=null){
            owner.getMaterials().add(materialSaved);
        }
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
    public List<Material> getMaterialList(String token,String status,int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Material> materialPage = null;
        Account account = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        Department department =account.getDepartment();
        Organization organization = organizationRepository.findById(department.getOrganization().getOrganizationId()).orElse(null);
        if(status.equals("All")){
            materialPage = materialRepository.findAllByOwner_Department_Organization_OrganizationIdOrderByCreatedAtDesc(organization.getOrganizationId(), pageRequest);
        }else{
             materialPage = materialRepository.findAllByOwner_Department_Organization_OrganizationIdAndActualStatusOrderByCreatedAtDesc(organization.getOrganizationId(),status,pageRequest);
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

    @Override
    public List<Material> addOwnerForMaterials(String token, int page, int size) {
        List<Material> materials = materialRepository.findAll();
        Account owner = accountRepository.findById(authService.decodeToken(token)).orElse(null);
        materials.forEach(material -> {
            material.setOwner(owner);
            if(owner != null){
                owner.getMaterialsCreated().add(material);
            }
            materialRepository.save(material);
        });
        assert owner != null;
        accountRepository.save(owner);
        return materials;
    }

    @Override
    public String deleteMaterial(String materialId) {
        Material material = materialRepository.findById(materialId).orElse(null);
        if(material != null){
            Account account = accountRepository.findById(material.getOwner().getAccountId()).orElse(null);
            account.getMaterials().remove(material);
            material.setAccounts(new ArrayList<>());
            material.setMaterialStatus(null);
            material.setOwner(null);
            material.setHistories(new ArrayList<>());
        }
        materialRepository.deleteById(materialId);
        return "Material deleted successfully";
    }
}
