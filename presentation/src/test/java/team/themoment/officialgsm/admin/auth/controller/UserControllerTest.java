package team.themoment.officialgsm.admin.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    ModifyNameUseCase modifyNameUseCase;

    @Mock
    AuthDataMapper authDataMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    void nameModify() throws Exception {
        // given
        UserNameModifyRequest request = new UserNameModifyRequest("신희성");

        // when & then
        mockMvc.perform(patch("/api/auth/username")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .cookie(new Cookie("access_token", "0"))
//                        .cookie(new Cookie("refresh_token", "0"))
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}

