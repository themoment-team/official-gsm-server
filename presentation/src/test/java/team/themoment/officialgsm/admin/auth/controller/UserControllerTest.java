package team.themoment.officialgsm.admin.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.common.util.CookieUtil;
import team.themoment.officialgsm.domain.auth.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.usecase.*;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    FindUserInfoUseCase findUserInfoUseCase;

    @Mock
    ModifyNameUseCase modifyNameUseCase;

    @Mock
    LogoutUseCase logoutUseCase;

    @Mock
    TokenReissueUseCase tokenReissueUseCase;

    @Mock
    UnapprovedListUseCase unapprovedListUseCase;

    @Mock
    ApprovedUseCase approvedUseCase;

    @Mock
    RefuseApprovedUseCase refuseApprovedUseCase;

    @Mock
    CookieUtil cookieUtil;

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
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void userInfoFind() throws Exception {
        // given
        UserInfoDto mockUserInfoDto = new UserInfoDto("신희성", Role.ADMIN, "s23012@gsm.hs.kr");
        UserInfoResponse mockUserInfoResponse = new UserInfoResponse("신희성", Role.ADMIN, "s23012@gsm.hs.kr");

        given(findUserInfoUseCase.execute()).willReturn(mockUserInfoDto);
        given(userDataMapper.toInfoResponse(any(UserInfoDto.class))).willReturn(mockUserInfoResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/auth/userinfo"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("신희성"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.userEmail").value("s23012@gsm.hs.kr"));

        verify(findUserInfoUseCase, times(1)).execute();
        verify(userDataMapper, times(1)).toInfoResponse(mockUserInfoDto);
    }

    @Test
    void logout() throws Exception {
        // given
        String accessToken = "0";

        given(cookieUtil.getCookieValue(any(), eq(ConstantsUtil.accessToken))).willReturn(accessToken);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/auth/logout"));

        // then
        resultActions.andExpect(status().isNoContent());

        verify(logoutUseCase, times(1)).execute(accessToken);
    }

    @Test
    void tokenReissue() throws Exception {
        // given
        String token = "0";

        given(cookieUtil.getCookieValue(any(), eq(ConstantsUtil.refreshToken))).willReturn(token);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/auth/token/refresh"));

        // then
        resultActions
                .andExpect(status().isOk());

        verify(tokenReissueUseCase, times(1)).execute(eq(token), any());
    }

    @Test
    void unapprovedListFind() throws Exception {
        // given
        UnapprovedListDto unapprovedListDto = new UnapprovedListDto("0", "신희성", Role.ADMIN, LocalDateTime.now());
        UnapprovedListResponse unapprovedListResponse = new UnapprovedListResponse("0", "신희성", Role.ADMIN, "");

        given(unapprovedListUseCase.execute()).willReturn(List.of(unapprovedListDto));
        given(userDataMapper.toUnapprovedListResponse(List.of(unapprovedListDto))).willReturn(List.of(unapprovedListResponse));

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/auth/unapproved/list"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].userName").value(unapprovedListResponse.getUserName()))
                .andExpect(jsonPath("$[0].role").value("ADMIN"))
                .andExpect(jsonPath("$[0].requestedAt").value(unapprovedListResponse.getRequestedAt()));

        verify(unapprovedListUseCase, times(1)).execute();
        verify(userDataMapper, times(1)).toUnapprovedListResponse(List.of(unapprovedListDto));
    }

    @Test
    void approved() throws Exception {
        // given
        String oauthId = "0";

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/auth/approved/" + oauthId));

        // then
        resultActions.andExpect(status().isCreated());

        verify(approvedUseCase, times(1)).execute(oauthId);
    }

    @Test
    void refuseApproved() throws Exception {
        // given
        String oauthId = "0";

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/auth/approved/" + oauthId));

        // then
        resultActions.andExpect(status().isNoContent());

        verify(refuseApprovedUseCase, times(1)).execute(oauthId);
    }
}

