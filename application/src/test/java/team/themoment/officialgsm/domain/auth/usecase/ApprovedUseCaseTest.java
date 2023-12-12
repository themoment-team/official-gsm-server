package team.themoment.officialgsm.domain.auth.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import team.themoment.officialgsm.common.util.UserUtil;
import team.themoment.officialgsm.domain.user.Role;
import team.themoment.officialgsm.domain.user.User;
import team.themoment.officialgsm.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApprovedUseCaseTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserUtil userUtil;

    @InjectMocks
    private ApprovedUseCase approvedUseCase;

    @Test
    public void testExecute() {
    }
}