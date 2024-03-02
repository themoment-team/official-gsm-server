package team.themoment.officialgsm.admin.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.themoment.officialgsm.admin.controller.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.controller.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.controller.auth.manager.UserManager;
import team.themoment.officialgsm.admin.controller.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.usecase.FindUserInfoUseCase;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.user.Role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    ModifyNameUseCase modifyNameUseCase;

    @Mock
    FindUserInfoUseCase findUserInfoUseCase;

    @Mock
    UserManager userManager;

    @Mock
    AuthDataMapper userDataMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    void nameModify() throws Exception {
        // given
        UserNameModifyRequest request = new UserNameModifyRequest("신희성");

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/auth/username")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)));


        // then
        resultActions.andExpect(status().isOk());

        verify(modifyNameUseCase, times(1)).execute(eq(userDataMapper.toDto(request)), any());
    }

    @Test
    void userInfoFind() throws Exception {
        // given
        UserInfoDto mockUserInfoDto = new UserInfoDto("신희성", Role.ADMIN, "s23012@gsm.hs.kr");
        UserInfoResponse mockUserInfoResponse = new UserInfoResponse("신희성", Role.ADMIN, "s23012@gsm.hs.kr");

        given(findUserInfoUseCase.execute(any())).willReturn(mockUserInfoDto);
        given(userDataMapper.toInfoResponse(any(UserInfoDto.class))).willReturn(mockUserInfoResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/auth/userinfo"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("신희성"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.userEmail").value("s23012@gsm.hs.kr"));

        verify(findUserInfoUseCase, times(1)).execute(any());
        verify(userDataMapper, times(1)).toInfoResponse(mockUserInfoDto);
    }
}