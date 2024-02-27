package team.themoment.officialgsm.admin.controller.approve;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.themoment.officialgsm.admin.controller.approve.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.controller.approve.mapper.ApproveDataMapper;
import team.themoment.officialgsm.admin.controller.auth.manager.UserManager;
import team.themoment.officialgsm.domain.approve.usecase.ApprovedUseCase;
import team.themoment.officialgsm.domain.approve.usecase.UnapprovedListUseCase;
import team.themoment.officialgsm.domain.user.User;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApproveController {

    private final UnapprovedListUseCase unapprovedListUseCase;
    private final ApprovedUseCase approvedUseCase;

    private final ApproveDataMapper approveDataMapper;
    private final UserManager userManager;


    @GetMapping("/unapproved/list")
    public ResponseEntity<List<UnapprovedListResponse>> unapprovedListFind() {
        return ResponseEntity.ok(approveDataMapper.toUnapprovedListResponse(unapprovedListUseCase.execute()));
    }

    @PatchMapping("/approved/{oauthId}")
    public ResponseEntity<Void> approved(@PathVariable String oauthId) {
        User grantor = userManager.getCurrentUser();
        approvedUseCase.execute(oauthId, grantor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
