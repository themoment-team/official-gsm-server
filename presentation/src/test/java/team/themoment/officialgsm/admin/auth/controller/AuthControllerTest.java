package team.themoment.officialgsm.admin.auth.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import static javax.swing.UIManager.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ModifyNameUseCase modifyNameUseCase;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void nameModify() throws Exception {
        // Given
        User user = userRepository.save(new User(
                0L,
                "0",
                "신희성",
                "s23012@gsm.hs.kr",
                Role.UNAPPROVED,
                null,
                null,
                null));

        // When
        ResultActions resultActions = mockMvc.perform(
                patch("/api/auth/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"시니성\"}"));

        // Then
        resultActions.andExpect(status().isOk());

        User updatedUser = userRepository.findByOauthId(user.oauthId()).orElse(null);
        assert updatedUser != null;
        assert updatedUser.userName().equals("시니성");
    }
}