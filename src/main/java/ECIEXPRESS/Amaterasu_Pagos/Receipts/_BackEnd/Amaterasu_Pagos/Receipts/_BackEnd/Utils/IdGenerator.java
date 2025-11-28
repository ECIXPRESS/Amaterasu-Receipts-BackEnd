package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils;

import java.security.SecureRandom;
import java.util.Random;

public class IdGenerator {
    private static final String BASE36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new SecureRandom();

    public static String generate() {
        StringBuilder sb = new StringBuilder(9);
        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(BASE36.length());
            sb.append(BASE36.charAt(index));
        }
        return sb.toString();
    }
}
