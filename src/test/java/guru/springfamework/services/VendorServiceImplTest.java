package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by Pierrot on 7/15/21.
 */
public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepoMock;

    @InjectMocks
    VendorServiceImpl vendorSrv;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vendorSrv = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepoMock);
    }

    @Test
    public void getVendorByID() {
        final String vendorName = "Mock Inc.";

        // Given
        Vendor vendorMock = new Vendor();
        vendorMock.setName(vendorName);

        when(vendorRepoMock.findById(anyLong())).thenReturn(Optional.of(vendorMock));

        // When
        VendorDTO vendorByID = vendorSrv.getVendorByID(1L);

        // Then
        assertThat(vendorByID.getName()).isEqualTo(vendorName);
    }

    @Test
    public void getAllVendors() {
        // Given
        Vendor vendor1 = new Vendor();
        vendor1.setName("Banana Mock Ltd");
        vendor1.setId(1L);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Guava Mock Ltd.");
        vendor2.setId(2L);

        Vendor vendor3 = new Vendor();
        vendor3.setName("Ghana Mock Inc.");
        vendor3.setId(3L);

        when(vendorRepoMock.findAll()).thenReturn(Arrays.asList(vendor1,vendor2,vendor3));

        // When
        VendorListDTO allVendorDTOs = vendorSrv.getAllVendors();

        // Then
        assertThat(allVendorDTOs.getVendors()).hasSize(3);
        assertThat(allVendorDTOs.getVendors().get(2).getName()).isEqualTo(vendor3.getName());
        // Just for Documentation. Doesn't work at moment
        assertThat(allVendorDTOs.getVendors()).extracting("vendorUrl").isNotNull();
    }
}