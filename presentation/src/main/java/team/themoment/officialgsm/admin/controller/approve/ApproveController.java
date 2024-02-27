package team.themoment.officialgsm.admin.controller.approve;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.themoment.officialgsm.admin.controller.approve.dto.response.UnapprovedListResponse;
import team.themoment.officialgsm.admin.controller.approve.mapper.ApproveDataMapper;
import team.themoment.officialgsm.domain.approve.usecase.UnapprovedListUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApproveController {

    private final UnapprovedListUseCase unapprovedListUseCase;

    private final ApproveDataMapper approveDataMapper;


    @GetMapping("/unapproved/list")
    public ResponseEntity<List<UnapprovedListResponse>> unapprovedListFind() {
        return ResponseEntity.ok(approveDataMapper.toUnapprovedListResponse(unapprovedListUseCase.execute()));
    }
}
