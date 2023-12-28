package team.themoment.officialgsm.admin.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.mapper.AuthDataMapper;
import team.themoment.officialgsm.common.util.ConstantsUtil;
import team.themoment.officialgsm.common.util.CookieUtil;
import team.themoment.officialgsm.domain.auth.dto.UserInfoDto;
import team.themoment.officialgsm.domain.auth.usecase.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final ModifyNameUseCase modifyNameUseCase;
    private final FindUserInfoUseCase findUserInfoUseCase;
    private final CookieUtil cookieUtil;
    private final LogoutUseCase logoutUseCase;
    private final TokenReissueUseCase tokenReissueUseCase;
    private final AuthDataMapper userDataMapper;
    private final UnapprovedListUseCase unapprovedListUseCase;
    private final ApprovedUseCase approvedUseCase;
    private final RefuseApprovedUseCase refuseApprovedUseCase;

    @PatchMapping("/username")
    public ResponseEntity<Void> nameModify(
            @Valid @RequestBody UserNameModifyRequest request
    ) {
        modifyNameUseCase.execute(userDataMapper.toDto(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoResponse> userInfoFind() {
        UserInfoDto userInfoDto = findUserInfoUseCase.execute();
        return ResponseEntity.ok(userDataMapper.toInfoResponse(userInfoDto));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = cookieUtil.getCookieValue(request, ConstantsUtil.accessToken);
        logoutUseCase.execute(accessToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<Void> tokenReissue (
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String token = cookieUtil.getCookieValue(request, ConstantsUtil.refreshToken);
        tokenReissueUseCase.execute(token, response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unapproved/list")
    public ResponseEntity<List<UnapprovedListResponse>> unapprovedListFind() {
        return ResponseEntity.ok(userDataMapper.toUnapprovedListResponse(unapprovedListUseCase.execute()));
    }

    @PatchMapping("/approved/{oauthId}")
    public ResponseEntity<Void> approved(@PathVariable String oauthId) {
        approvedUseCase.execute(oauthId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/approved/{oauthId}")
    public ResponseEntity<Void> refuseApproved(@PathVariable String oauthId) {
        refuseApprovedUseCase.execute(oauthId);
        return ResponseEntity.noContent().build();
    }
}
