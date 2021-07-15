package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;


/**
 * Created by Pierrot on 7/15/21.
 */
public interface VendorService {

    VendorDTO getVendorByID(Long id);

    VendorListDTO getAllVendors();

    VendorDTO createNewVendor(VendorDTO vendorDTO);

}
