package team.themoment.officialgsm.client.auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("OK!");
    }
}
