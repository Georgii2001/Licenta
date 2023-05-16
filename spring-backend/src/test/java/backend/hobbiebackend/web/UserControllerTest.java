package backend.hobbiebackend.web;

import backend.hobbiebackend.controller.UserController;
import backend.hobbiebackend.dto.AppClientSignUpDto;
import backend.hobbiebackend.dto.UpdateAppClientDto;
import backend.hobbiebackend.dto.UpdateBusinessDto;
import backend.hobbiebackend.entities.UserEntity;
import backend.hobbiebackend.enums.GenderEnum;
import backend.hobbiebackend.enums.UserRoleEnum;
import backend.hobbiebackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends AbstractTest {
    @Autowired
    private UserController controller;
    private UpdateAppClientDto updateAppClientDto;
    private UpdateBusinessDto updateBusinessDto;
    private AppClientSignUpDto appClientSignUpDto;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        super.setUp();

        // prepare data client
        appClientSignUpDto = new AppClientSignUpDto();
        appClientSignUpDto.setUsername("user");
        appClientSignUpDto.setPassword("topsecret");
        appClientSignUpDto.setEmail("testemail@gmail.com");
        appClientSignUpDto.setFullName("full name");
        appClientSignUpDto.setGender(GenderEnum.FEMALE);

        //update client
        updateAppClientDto = new UpdateAppClientDto();
        updateAppClientDto.setEmail("test@gmail.com");
        updateAppClientDto.setPassword("topsecret");
        updateAppClientDto.setFullName("full name");
        updateAppClientDto.setGender(GenderEnum.FEMALE);

        //update business
        updateBusinessDto = new UpdateBusinessDto();
        updateBusinessDto.setId(1);
        updateBusinessDto.setBusinessName("Business Name");
        updateBusinessDto.setPassword("password");
        updateBusinessDto.setBusinessName("Bizz name");

        //prepare data user
        UserEntity user = new UserEntity();
        user.setRole(UserRoleEnum.ADMIN.name());

    }

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void signup_should_work() throws Exception {
        String uri = "/signup";

        String inputJson = super.mapToJson(appClientSignUpDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void update_user_should_work() throws Exception {
        String uri = "/user";

        String inputJson = super.mapToJson(updateAppClientDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
}
