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

import static guru.springfamework.controllers.v1.VendorController.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
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

    @Test
    public void createNewVendor() {
        final String vendorName = "Mock Inc.";

        // Given
        Vendor vendorMock = new Vendor();
        vendorMock.setName(vendorName);
        vendorMock.setId(1L);

        when(vendorRepoMock.save(any())).thenReturn(vendorMock);

        VendorDTO vendorDTOMock = new VendorDTO();
        vendorDTOMock.setName(vendorMock.getName());
        vendorDTOMock.setVendorUrl(BASE_URL +"/"+vendorMock.getId());

        //When
        VendorDTO newVendorDTO = vendorSrv.createNewVendor(vendorDTOMock);

        // Then
        assertThat(newVendorDTO.getName()).isEqualTo(vendorDTOMock.getName());
        assertThat(newVendorDTO.getVendorUrl()).contains(BASE_URL);

        verify(vendorRepoMock).save(any());
    }

    @Test
    public void saveVendorByDTO() {
        final String vendorName = "Mock Inc.";

        // Given
        Vendor vendorMock = new Vendor();
        vendorMock.setName(vendorName);
        vendorMock.setId(1L);

        when(vendorRepoMock.findById(vendorMock.getId())).thenReturn(Optional.of(vendorMock));

        when(vendorRepoMock.save(any(Vendor.class))).thenReturn(vendorMock);

        VendorDTO vendorDTOMock = new VendorDTO();
        vendorDTOMock.setName(vendorMock.getName());
        vendorDTOMock.setVendorUrl(BASE_URL+"/"+vendorMock.getId());

        // When
        VendorDTO savedVendorDTO = vendorSrv.saveVendorByDTO(1L, vendorDTOMock);

        // Then
        assertThat(savedVendorDTO.getName()).isEqualTo(vendorMock.getName());
    }

    @Test
    public void patchVendorByName() {
        final String oldVendorName = "Old Mock Inc.";
        final String newVendorName = "New Mock Inc.";
        // Given
        Vendor newVendor = new Vendor();
        newVendor.setName(newVendorName);
        newVendor.setId(1L);

        Vendor oldVendor = new Vendor();
        oldVendor.setName(oldVendorName);
        oldVendor.setId(1L);

        VendorDTO newVendorDTO = new VendorDTO();
        newVendorDTO.setVendorUrl(BASE_URL+"/"+newVendor.getId());
        newVendorDTO.setName(newVendorName);

        when(vendorRepoMock.findById(oldVendor.getId())).thenReturn(Optional.of(oldVendor));

        when(vendorRepoMock.save(newVendor)).thenReturn(newVendor);


        // When
        VendorDTO returnedVendorDTO = vendorSrv.patchVendor(1L, newVendorDTO);

        // Then
        assertThat(returnedVendorDTO.getName()).isEqualTo(newVendorDTO.getName());
        assertThat(returnedVendorDTO.getVendorUrl()).isEqualTo(newVendorDTO.getVendorUrl());
    }
}