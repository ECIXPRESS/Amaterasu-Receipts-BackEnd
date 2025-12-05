package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@UtilityClass
public class SimpleEncryptionUtil {
    @Value("${qr.encryption.password}") String password;
    @Value("${qr.encryption.salt}") String salt;
    private final TextEncryptor encryptor = Encryptors.text(password, salt);


    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
