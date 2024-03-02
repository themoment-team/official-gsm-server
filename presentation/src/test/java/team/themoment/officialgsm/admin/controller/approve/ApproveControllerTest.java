package team.themoment.officialgsm.admin.controller.approve;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.themoment.officialgsm.admin.controller.approve.mapper.ApproveDataMapper;

@ExtendWith(MockitoExtension.class)
class ApproveControllerTest {

    @InjectMocks
    ApproveController approveController;

    @Mock
    ApproveDataMapper approveMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(approveController)
                .build();
    }
}