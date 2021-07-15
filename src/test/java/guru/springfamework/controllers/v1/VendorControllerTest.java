package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static guru.springfamework.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
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
}