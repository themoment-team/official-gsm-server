package team.themoment.officialgsm.admin.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthControllerTest.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();

        userRepository.deleteAll();
    }


    @Test
    void nameModify() throws Exception {
        // given
        User user = User.builder()
                .oauthId("0")
                .userEmail("s23012@gsm.hs.kr")
                .role(Role.UNAPPROVED)
                .grantor(null)
                .approvedAt(null)
                .requestedAt(null)
                .userName(null)
                .build();
        userRepository.save(user);

        final String url = "/api/auth/username";
        final String userName = "시니성";

        // when
        final ResultActions result = mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userName)));

        // then
        result
                .andExpect(status().isOk());
    }

    @Test
    void testNameModify() {
    }

    @Test
    void userInfoFind() {
    }

    @Test
    void logout() {
    }

    @Test
    void test1() {
    }
}