package it;

import com.example.rentacar.RentacarApplication;
import com.example.rentacar.client.ClientInputInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RentacarApplication.class)
@AutoConfigureMockMvc
public class ApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void add_whenNoModel_thenReturnErrorMsg() throws Exception {
        String name = "user1";
        ClientInputInfo input = new ClientInputInfo(
                name, 1990,
                "model-x", 2015);
        mockMvc.perform(postRequest(input))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("No car model:")));
    }

    @Test
    public void add_whenNewClient_thenAddClient() throws Exception {
        String name = "user2";
        ClientInputInfo input = new ClientInputInfo(
                name, 1990,
                "bmw", 2015);
        mockMvc.perform(postRequest(input))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    public void add_whenExistingClient_thenReturnErrorMsg() throws Exception {
        String name = "user3";
        ClientInputInfo input = new ClientInputInfo(
                name, 1990,
                "bmw", 2015);
        MockHttpServletRequestBuilder req = postRequest(input);
        mockMvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));


        mockMvc.perform(req)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("already exists")));
    }


    private MockHttpServletRequestBuilder postRequest(ClientInputInfo input) throws JsonProcessingException {
        String inputJson = new ObjectMapper().writeValueAsString(input);
        return post("/clients")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    @Test
    public void delete_whenNoClient_thenReturnErrorMsg() throws Exception {
        String name = "user4";
        String model = "bmw";
        mockMvc.perform(deleteRequest(name, model))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("No client")));
    }

    @Test
    public void delete_whenNotRented_thenReturnErrorMsg() throws Exception {
        String name = "user5";
        String model = "bmw";
        ClientInputInfo input = new ClientInputInfo(
                name, 1990,
                model, 2015);
        mockMvc.perform(postRequest(input));
        mockMvc.perform(deleteRequest(name, "audi"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("not rented")));
    }

    @Test
    public void delete_whenCarRented_thenRemoveClient() throws Exception {
        String name = "user6";
        String model = "bmw";
        ClientInputInfo input = new ClientInputInfo(
                name, 1990,
                model, 2015);
        mockMvc.perform(postRequest(input));
        mockMvc.perform(deleteRequest(name, model))
                .andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder deleteRequest(String client, String model) {
        return MockMvcRequestBuilders.delete("/clients")
                .param("name", client)
                .param("model", model);
    }
}
