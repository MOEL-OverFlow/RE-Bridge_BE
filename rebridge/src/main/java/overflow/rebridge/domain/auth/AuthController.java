package overflow.rebridge.domain.auth;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.auth.dto.*;
import overflow.rebridge.domain.member.Member;
import overflow.rebridge.domain.member.MemberRepository;
import overflow.rebridge.global.security.jwt.JwtTokenProvider;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final GoogleOAuthService googleOAuthService;
    private final AuthService authService;
    private final EmailVerificationService emailVerificationService;

    // 일반 로그인 API (email + password)
    @PostMapping("/login/local")
    @Operation(summary = "일반 로그인")
    public ResponseEntity<LoginResponse> login(@RequestBody LocalLoginRequest localLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(localLoginRequest.email(), localLoginRequest.password())
        );

        Member member = memberRepository.findByEmail(localLoginRequest.email())
                .orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(localLoginRequest.email());

        refreshTokenService.saveOrUpdate(member, refreshToken);

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
    }

    // 구글 로그인 API (accessToken 전달)
    @PostMapping("/login/google")
    @Operation(summary = "구글 로그인")
    public ResponseEntity<GoogleLoginResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        String googleAccessToken = request.accessToken();

        Member member = googleOAuthService.loginWithGoogle(googleAccessToken);

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getEmail());

        GoogleLoginResponse googleLoginResponse = new GoogleLoginResponse(member.getName(), member.getEmail(), member.getRole().toString(), member.getLoginType().toString(),accessToken, refreshToken );

        refreshTokenService.saveOrUpdate(member, refreshToken);

        return ResponseEntity.ok(googleLoginResponse);
    }

    // 토큰 재발급 API
    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<?> reissue(@RequestHeader("Authorization") String refreshHeader) {
        String refreshToken = refreshHeader.replace("Bearer ", "");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 리프레시 토큰");
        }

        String email = jwtTokenProvider.getEmail(refreshToken);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        RefreshToken savedToken = refreshTokenRepository.findByMember(member)
                .orElseThrow(() -> new IllegalStateException("리프레시 토큰이 존재하지 않습니다."));

        if (!savedToken.getToken().equals(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리프레시 토큰 불일치");
        }

        String newAccessToken = jwtTokenProvider.createAccessToken(member);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            authService.signup(request);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/findId")
    @Operation(summary = "아이디 찾기")
    public ResponseEntity<?> findId(@RequestBody FindIdRequest request) {
        try {
            String email = authService.findid(request);
            return ResponseEntity.ok(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/send-verification")
    @Operation(summary = "이메일 인증번호 전송")
    public ResponseEntity<?> sendVerificationCode(@RequestBody EmailVerificationRequest verificationRequest) {
        emailVerificationService.sendVerificationCode(verificationRequest.email());
        return ResponseEntity.ok("인증번호가 이메일로 전송되었습니다.");
    }

    @PostMapping("/verify-code")
    @Operation(summary = "이메일 인증번호 확인")
    public ResponseEntity<?> verifyCode(@RequestBody EmailVerifyRequest request) {
        boolean result = emailVerificationService.verifyCode(request.email(), request.code());

        if (result) {
            return ResponseEntity.ok("인증 성공");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 일치하지 않습니다.");
        }
    }

}
