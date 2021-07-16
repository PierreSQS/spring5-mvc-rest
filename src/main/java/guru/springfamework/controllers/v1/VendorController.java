package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("{id}")
    public VendorDTO getVendorByID(@PathVariable Long id) {
        return vendorService.getVendorByID(id);
    }

    @GetMapping
    public VendorListDTO getAllVendors() {
        return vendorService.getAllVendors();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("{id}")
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO newVendorDTO) {
        return vendorService.saveVendorByDTO(id,newVendorDTO);
    }

    @PatchMapping("{id}")
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO newVendorDTO) {
        return vendorService.patchVendor(id,newVendorDTO);
    }

    @DeleteMapping("{id}")
    public void deleteVendorByID(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
