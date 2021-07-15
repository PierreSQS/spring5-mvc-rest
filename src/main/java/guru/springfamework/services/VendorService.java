package guru.springfamework.services;

import guru.springfamework.api.v1.model.VendorDTO;


/**
 * Created by Pierrot on 7/15/21.
 */
public interface VendorService {

    VendorDTO getVendorByID(Long id);

}
