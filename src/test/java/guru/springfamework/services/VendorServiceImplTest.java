package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}