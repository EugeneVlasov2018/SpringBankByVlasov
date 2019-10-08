package vlasovspringbanksystem.utils.generators;

import org.springframework.stereotype.Component;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.utils.exceptions.PasswordException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class PasswordGenerator {

    public Map<String, String> createPasswordAndSalt(String password) {
        Map<String, String> result = new HashMap<>();
        String salt = generateSalt();

        result.put("salt", salt);
        result.put("password", workWithPassAndSalt(password, salt));

        return result;
    }

    private String generateSalt() {
        return UUID.randomUUID().toString().substring(0, 16);
    }

    public boolean checkPassword(User user, String innerPassword) {
        String userPassword = user.getUsersPassword();
        String salt = user.getSalt();

        return Objects.equals(workWithPassAndSalt(innerPassword, salt), userPassword);
    }

    private String workWithPassAndSalt(String password, String salt) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Arrays.toString(cipher.doFinal(password.getBytes()));


        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException |
                BadPaddingException |
                IllegalBlockSizeException |
                InvalidKeyException e) {
            throw new PasswordException(e);
        }
    }
}
