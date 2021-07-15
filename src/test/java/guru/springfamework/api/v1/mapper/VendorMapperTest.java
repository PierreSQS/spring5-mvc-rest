package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VendorMapperTest {

    public static final String VENDOR_NAME = "Pierrot Fresh Fruits from France Ltd.";

    private VendorMapper vendorMapper;

    @Before
    public void setUp() {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void testVendorToVendorDTO() {
        // Given
        Vendor vendor = new Vendor();
        vendor.setName(VENDOR_NAME);

        // When
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // Then
        assertThat(vendorDTO.getName()).isEqualTo(VENDOR_NAME);
    }

    @Test
    public void testVendorDtoToVendor() {
        // Given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDOR_NAME);

        // When
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        // Then
        assertThat(vendor.getName()).isEqualTo(VENDOR_NAME);
    }
}