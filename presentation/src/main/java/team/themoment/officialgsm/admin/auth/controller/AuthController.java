package team.themoment.officialgsm.admin.auth.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.officialgsm.admin.auth.dto.request.UserNameModifyRequest;
import team.themoment.officialgsm.admin.auth.dto.response.UserInfoResponse;
import team.themoment.officialgsm.admin.auth.mapper.UserNameDataMapper;
import team.themoment.officialgsm.domain.auth.dto.UserNameDto;
import team.themoment.officialgsm.domain.auth.usecase.ModifyNameUseCase;
import team.themoment.officialgsm.domain.user.Role;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ModifyNameUseCase modifyNameUseCase;
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
    public ResponseEntity<UserInfoResponse>  userInfoFind() {
        return ResponseEntity.ok().build();
    }
}
