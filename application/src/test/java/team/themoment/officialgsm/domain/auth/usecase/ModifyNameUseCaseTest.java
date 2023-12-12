package team.themoment.officialgsm.domain.auth.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ModifyNameUseCaseTest {

    @Mock
    private UserUtil userUtil;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ModifyNameUseCase modifyNameUseCase;

    @Test
    void execute() {
        // given
        UserNameDto dto = new UserNameDto("신희성");
        User currentUser = new User("0", null, "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);
        User modifiedUser = new User("0", "신희성", "s23012@gsm.hs.kr", Role.UNAPPROVED, null, null, null);

        given(userUtil.getCurrentUser()).willReturn(currentUser);

        // when
        modifyNameUseCase.execute(dto);

        // then
        verify(userRepository, times(1)).save(modifiedUser);
    }
}