package overflow.rebridge.domain.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final JavaMailSender mailSender;
    private final Map<String, String> verificationMap = new ConcurrentHashMap<>();

    public void sendVerificationCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        verificationMap.put(email, code);

        String subject = "[RE-Bridge] 이메일 인증번호 안내";
        String body = String.format("""
                안녕하세요.

                RE-Bridge 인증번호는 다음과 같습니다:

                ✅ 인증번호: %s

                감사합니다.
                """, code);

        try {
            sendEmail(email, subject, body);
        } catch (MessagingException e) {
            throw new IllegalStateException("이메일 전송에 실패했습니다.");
        }
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationMap.get(email));
    }

    private void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);
        mailSender.send(message);
    }
}
