package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(VendorController.class)
public class VendorControllerTest {

    @MockBean
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
    }

    @Test
    public void getVendorByID() throws Exception {
        final String vendorName = "Mock Inc.";

        // Given
        VendorDTO vendorDTOMock = new VendorDTO();
        vendorDTOMock.setName(vendorName);
        vendorDTOMock.setVendorUrl(BASE_URL+"/1");

        when(vendorService.getVendorByID(anyLong())).thenReturn(vendorDTOMock);

        // When, Then
        mockMvc.perform(get(BASE_URL+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(vendorName)))
                .andExpect(jsonPath("$.vendor_url",equalTo(BASE_URL+"/1")))
                .andDo(print());
    }

    @Test
    public void getAllVendors() throws Exception {
        // Given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Mock1 Inc.");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Mock2 Inc.");

        VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(vendorDTO1,vendorDTO2));

        when(vendorService.getAllVendors()).thenReturn(vendorListDTO);
        // When, Then
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)))
                // Not accurate, just for documentation purpose!
//               .andExpect(jsonPath("$.vendors[1].vendor_url", is(not(nullValue()))))
                .andDo(print());
    }
}