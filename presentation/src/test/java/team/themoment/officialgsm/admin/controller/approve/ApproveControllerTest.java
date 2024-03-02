package team.themoment.officialgsm.admin.controller.approve;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.themoment.officialgsm.admin.controller.approve.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.controller.approve.mapper.ApproveDataMapper;
import team.themoment.officialgsm.admin.controller.auth.manager.UserManager;
import team.themoment.officialgsm.domain.approve.dto.UnapprovedListDto;
import team.themoment.officialgsm.domain.approve.usecase.ApprovedUseCase;
import team.themoment.officialgsm.domain.approve.usecase.UnapprovedListUseCase;
import team.themoment.officialgsm.domain.user.Role;

import java.time.LocalDateTime;
import java.util.List;

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
class ApproveControllerTest {

    @InjectMocks
    ApproveController approveController;

    @Mock
    UnapprovedListUseCase unapprovedListUseCase;

    @Mock
    ApprovedUseCase approvedUseCase;

    @Mock
    UserManager userManager;

    @Mock
    ApproveDataMapper approveMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(approveController)
                .build();
    }

    @Test
    void unapprovedListFind() throws Exception {
        // given
        UnapprovedListDto unapprovedListDto = new UnapprovedListDto("0", "신희성", Role.ADMIN, LocalDateTime.now());
        UnapprovedListResponse unapprovedListResponse = new UnapprovedListResponse("0", "신희성", Role.ADMIN, "");

        given(unapprovedListUseCase.execute()).willReturn(List.of(unapprovedListDto));
        given(approveMapper.toUnapprovedListResponse(List.of(unapprovedListDto))).willReturn(List.of(unapprovedListResponse));

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
        verify(approveMapper, times(1)).toUnapprovedListResponse(List.of(unapprovedListDto));
    }

    @Test
    void approved() throws Exception {
        // given
        String oauthId = "0";

        // when
        ResultActions resultActions = mockMvc.perform(patch("/api/auth/approved/" + oauthId));

        // then
        resultActions.andExpect(status().isCreated());

        verify(approvedUseCase, times(1)).execute(eq(oauthId), any());
    }
}