package team.themoment.officialgsm.admin.auth.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.mapper.UserNameDataMapper;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.common.util.CookieUtil;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;
import team.themoment.officialgsm.domain.auth.usecase.FindUserInfoUseCase;
import team.themoment.officialgsm.domain.auth.usecase.LogoutUseCase;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;

import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ModifyNameUseCase modifyNameUseCase;
    private final FindUserInfoUseCase findUserInfoUseCase;
    private final CookieUtil cookieUtil;
    private final LogoutUseCase logoutUseCase;
    private final UserNameDataMapper userNameDataMapper;

    @PatchMapping("/username")
    public ResponseEntity<Void> nameModify(
            @Valid @RequestBody UserNameModifyRequest request
    ) {
        // mapper 사용
        modifyNameUseCase.execute(new UserNameDto(request.getUserName()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfoFind() {
        // mapper 사용
        UserInfoDto userInfoDto = findUserInfoUseCase.execute();
        return ResponseEntity.ok(new UserInfoResponse(userInfoDto.getUserName(), userInfoDto.getRole(), userInfoDto.getUserEmail()));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = cookieUtil.getCookieValue(request, ConstantsUtil.accessToken);
        logoutUseCase.execute(accessToken);
        return ResponseEntity.noContent().build();
    }
}
