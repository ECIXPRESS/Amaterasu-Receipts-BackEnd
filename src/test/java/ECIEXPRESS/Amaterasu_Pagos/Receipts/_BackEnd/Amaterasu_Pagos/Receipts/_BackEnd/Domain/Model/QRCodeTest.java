package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class QRCodeTest {

    @Test
    void qrCode_ShouldSetAndGetAllFields() {
        // Given
        String orderId = "order123";
        String receiptGeneratedDate = Instant.now().toString();
        String paymentProcessedAt = Instant.now().plusSeconds(3600).toString();

        // When
        QRCode qrCode = new QRCode();
        qrCode.setOrderId(orderId);
        qrCode.setReceiptGeneratedDate(receiptGeneratedDate);
        qrCode.setPaymentProcessedAt(paymentProcessedAt);

        // Then
        assertAll(
                () -> assertEquals(orderId, qrCode.getOrderId()),
                () -> assertEquals(receiptGeneratedDate, qrCode.getReceiptGeneratedDate()),
                () -> assertEquals(paymentProcessedAt, qrCode.getPaymentProcessedAt())
        );
    }

    @Test
    void qrCode_WithAllArgsConstructor_ShouldSetAllFields() {
        // Given
        String orderId = "order123";
        String receiptGeneratedDate = Instant.now().toString();
        String paymentProcessedAt = Instant.now().plusSeconds(3600).toString();

        // When
        QRCode qrCode = new QRCode();
        qrCode.setOrderId(orderId);
        qrCode.setReceiptGeneratedDate(receiptGeneratedDate);
        qrCode.setPaymentProcessedAt(paymentProcessedAt);

        // Then
        assertAll(
                () -> assertEquals(orderId, qrCode.getOrderId()),
                () -> assertEquals(receiptGeneratedDate, qrCode.getReceiptGeneratedDate()),
                () -> assertEquals(paymentProcessedAt, qrCode.getPaymentProcessedAt())
        );
    }
}