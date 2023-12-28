package team.themoment.officialgsm.admin.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.usecase.FindUserInfoUseCase;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    FindUserInfoUseCase findUserInfoUseCase;

    @Mock
    AuthDataMapper userDataMapper;

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
                        .cookie(new Cookie("access_token", "0"))
                        .cookie(new Cookie("refresh_token", "0"))
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUserInfoFind() {
        // given
        UserInfoDto mockUserInfoDto = new UserInfoDto("신희성", Role.ADMIN, "s23012@gsm.hs.kr");
        UserInfoResponse mockUserInfoResponse = new UserInfoResponse("신희성", Role.ADMIN, "s23012@gsm.hs.kr");

        given(findUserInfoUseCase.execute()).willReturn(mockUserInfoDto);
        given(userDataMapper.toInfoResponse(any(UserInfoDto.class))).willReturn(mockUserInfoResponse);

        // when
        ResponseEntity<UserInfoResponse> responseEntity = userController.userInfoFind();

        // then
        UserInfoResponse actualUserInfoResponse = responseEntity.getBody();

        assertEquals(mockUserInfoResponse, actualUserInfoResponse);
    }
}

