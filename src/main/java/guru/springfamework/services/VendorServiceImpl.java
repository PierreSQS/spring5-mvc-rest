package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

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
}
