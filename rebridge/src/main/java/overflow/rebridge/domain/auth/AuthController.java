package overflow.rebridge.domain.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import overflow.rebridge.domain.auth.dto.GoogleLoginRequest;
import overflow.rebridge.domain.auth.dto.GoogleLoginResponse;
import overflow.rebridge.domain.auth.dto.LocalLoginRequest;
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

    // 일반 로그인 API (email + password)
    @PostMapping("/login/local")
    public ResponseEntity<?> login(@RequestBody LocalLoginRequest localLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(localLoginRequest.email(), localLoginRequest.password())
        );

        Member member = memberRepository.findByEmail(localLoginRequest.email())
                .orElseThrow(() -> new IllegalStateException("회원 정보를 찾을 수 없습니다."));

        String accessToken = jwtTokenProvider.createAccessToken(member);
        String refreshToken = jwtTokenProvider.createRefreshToken(localLoginRequest.email());

        refreshTokenService.saveOrUpdate(member, refreshToken);

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));
    }

    // 구글 로그인 API (accessToken 전달)
    @PostMapping("/login/google")
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
}
