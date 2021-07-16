package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static guru.springfamework.controllers.v1.VendorController.BASE_URL;

/**
 * Created by Pierrot on 7/15/21.
 */
@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public VendorDTO getVendorByID(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {vendorDTO.setVendorUrl(BASE_URL+"/"+id);
                        return vendorDTO;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorListDTO getAllVendors() {
        List<VendorDTO> vendorDTOList = vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(BASE_URL+"/"+vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());
        return new VendorListDTO(vendorDTOList);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendorRepository.save(vendor);

        vendorDTO.setVendorUrl(BASE_URL+"/"+vendor.getId());
        return vendorDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

        return vendorRepository.findById(id).map(vendor -> {
            vendor.setName(vendorDTO.getName());

            VendorDTO returnedVendorDTO =
                    vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnedVendorDTO.setVendorUrl(BASE_URL + "/" + vendor.getId());

            return returnedVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }
}
