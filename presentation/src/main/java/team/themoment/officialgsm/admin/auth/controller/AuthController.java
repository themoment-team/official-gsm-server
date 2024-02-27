package team.themoment.officialgsm.admin.auth.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.officialgsm.admin.auth.controller.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.controller.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.controller.manager.CookieManager;
import team.themoment.officialgsm.admin.auth.controller.manager.UserManager;
import team.themoment.officialgsm.admin.auth.controller.mapper.AuthDataMapper;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.domain.auth.dto.ReissueTokenDto;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.spi.TokenProvider;
import team.themoment.officialgsm.domain.auth.usecase.FindUserInfoUseCase;
import team.themoment.officialgsm.domain.auth.usecase.LogoutUseCase;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.auth.usecase.TokenReissueUseCase;
import team.themoment.officialgsm.domain.user.User;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ModifyNameUseCase modifyNameUseCase;
    private final FindUserInfoUseCase findUserInfoUseCase;
    private final LogoutUseCase logoutUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;

    private final UserManager userManager;
    private final CookieManager cookieManager;
    private final AuthDataMapper userDataMapper;
    private final TokenProvider tokenProvider;

    @PatchMapping("/username")
    public ResponseEntity<Void> nameModify(
            @Valid @RequestBody UserNameModifyRequest request
    ) {
        User user = userManager.getCurrentUser();
        modifyNameUseCase.execute(userDataMapper.toDto(request), user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfoFind() {
        User user = userManager.getCurrentUser();
        UserInfoDto userInfoDto = findUserInfoUseCase.execute(user);
        return ResponseEntity.ok(userDataMapper.toInfoResponse(userInfoDto));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = cookieManager.getCookieValue(request, ConstantsUtil.accessToken);
        User user = userManager.getCurrentUser();
        logoutUseCase.execute(accessToken, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<Void> tokenReissue (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = cookieManager.getCookieValue(request, ConstantsUtil.refreshToken);
        ReissueTokenDto reissueTokenDto = tokenReissueUseCase.execute(token);

        cookieManager.addTokenCookie(response, ConstantsUtil.accessToken, reissueTokenDto.getAccessToken(), tokenProvider.getACCESS_TOKEN_EXPIRE_TIME(), true);
        cookieManager.addTokenCookie(response, ConstantsUtil.refreshToken, reissueTokenDto.getRefreshToken(), tokenProvider.getREFRESH_TOKEN_EXPIRE_TIME(), true);

        return ResponseEntity.ok().build();
    }
}
