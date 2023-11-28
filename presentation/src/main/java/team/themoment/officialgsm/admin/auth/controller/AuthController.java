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
        modifyNameUseCase.execute(userNameDataMapper.toDto(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfoFind() {
        UserInfoDto userInfoDto = findUserInfoUseCase.execute();
        return ResponseEntity.ok(userNameDataMapper.toInfoResponse(userInfoDto));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = cookieUtil.getCookieValue(request, ConstantsUtil.accessToken);
        logoutUseCase.execute(accessToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("OK!");
    }
}
