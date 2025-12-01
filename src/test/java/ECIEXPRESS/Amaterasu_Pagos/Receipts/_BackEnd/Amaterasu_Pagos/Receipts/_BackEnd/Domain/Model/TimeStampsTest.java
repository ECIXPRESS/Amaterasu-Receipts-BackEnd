package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class TimeStampsTest {

    @Test
    void timeStamps_ShouldSetAndGetAllFields() {
        // Given
        String createdAt = "2023-01-01T10:00:00Z";
        String paymentProcessedAt = "2023-01-01T10:05:00Z";
        String receiptGeneratedDate = "2023-01-01T10:10:00Z";

        // When
        TimeStamps timeStamps = new TimeStamps();
        timeStamps.setCreatedAt(createdAt);
        timeStamps.setPaymentProcessedAt(paymentProcessedAt);
        timeStamps.setReceiptGeneratedDate(receiptGeneratedDate);

        // Then
        assertAll(
                () -> assertEquals(createdAt, timeStamps.getCreatedAt()),
                () -> assertEquals(paymentProcessedAt, timeStamps.getPaymentProcessedAt()),
                () -> assertEquals(receiptGeneratedDate, timeStamps.getReceiptGeneratedDate())
        );
    }

    @Test
    void timeStamps_WithAllArgsConstructor_ShouldSetAllFields() {
        // Given
        String createdAt = "2023-01-01T10:00:00Z";
        String paymentProcessedAt = "2023-01-01T10:05:00Z";
        String receiptGeneratedDate = "2023-01-01T10:10:00Z";

        // When
        TimeStamps timeStamps = new TimeStamps(createdAt, paymentProcessedAt, receiptGeneratedDate);

        // Then
        assertAll(
                () -> assertEquals(createdAt, timeStamps.getCreatedAt()),
                () -> assertEquals(paymentProcessedAt, timeStamps.getPaymentProcessedAt()),
                () -> assertEquals(receiptGeneratedDate, timeStamps.getReceiptGeneratedDate())
        );
    }

    @Test
    void timeStamps_NoArgsConstructor_ShouldCreateInstance() {
        // When
        TimeStamps timeStamps = new TimeStamps();

        // Then
        assertNotNull(timeStamps);
    }
}