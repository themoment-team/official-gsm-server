package team.themoment.officialgsm.admin.auth.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.themoment.officialgsm.admin.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.common.util.CookieUtil;
import team.themoment.officialgsm.domain.auth.usecase.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    private ModifyNameUseCase modifyNameUseCase;
    @MockBean
    private FindUserInfoUseCase findUserInfoUseCase;
    @MockBean
    private CookieUtil cookieUtil;
    @MockBean
    private LogoutUseCase logoutUseCase;
    @MockBean
    private TokenReissueUseCase tokenReissueUseCase;
    @MockBean
    private AuthDataMapper userDataMapper;
    @MockBean
    private UnapprovedListUseCase unapprovedListUseCase;
    @MockBean
    private ApprovedUseCase approvedUseCase;
    @MockBean
    private RefuseApprovedUseCase refuseApprovedUseCase;

    private final UserController userController = new UserController(modifyNameUseCase,
            findUserInfoUseCase,
            cookieUtil,
            logoutUseCase,
            tokenReissueUseCase,
            userDataMapper,
            unapprovedListUseCase,
            approvedUseCase,
            refuseApprovedUseCase
            );

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void nameModify() throws Exception {
    }
}