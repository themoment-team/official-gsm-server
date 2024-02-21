package team.themoment.officialgsm.admin.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.officialgsm.admin.auth.controller.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.controller.manager.UserManager;
import team.themoment.officialgsm.admin.auth.controller.mapper.AuthDataMapper;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.usecase.FindUserInfoUseCase;
import team.themoment.officialgsm.domain.user.User;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final FindUserInfoUseCase findUserInfoUseCase;
    private final UserManager userManager;
    private final AuthDataMapper userDataMapper;

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfoFind() {
        User user = userManager.getCurrentUser();
        UserInfoDto userInfoDto = findUserInfoUseCase.execute(user);
        return ResponseEntity.ok(userDataMapper.toInfoResponse(userInfoDto));
    }
}
