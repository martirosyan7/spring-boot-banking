package com.banking.springbootbanking.utils.generator;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CVVGenerator {

    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SECRET_KEY = "YourSecretKey";

    public static String generateCVV(String cardNumber, LocalDate expirationDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyy");
            String formattedDate = expirationDate.format(formatter);

            String data = cardNumber + formattedDate;
            Mac mac = Mac.getInstance(HMAC_ALGO);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
            mac.init(secretKeySpec);

            byte[] hmacResult = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            int hashValue = Math.abs(Hex.encodeHexString(hmacResult).hashCode());

            String hashString = String.valueOf(hashValue);
            return hashString.substring(0, 3);
        } catch (Exception e) {
            throw new RuntimeException("Error generating CVV", e);
        }
    }
}
