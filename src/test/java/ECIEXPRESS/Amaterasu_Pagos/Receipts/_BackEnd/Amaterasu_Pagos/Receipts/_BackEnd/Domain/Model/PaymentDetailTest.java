package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDetailTest {

    @Test
    void paymentDetail_ShouldSetAndGetAllFields() {
        // Given
        String bankReceiptNumber = "BANK123";
        double originalAmount = 100.0;
        double finalAmount = 90.0;

        // When
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setBankReceiptNumber(bankReceiptNumber);
        paymentDetail.setOriginalAmount(originalAmount);
        paymentDetail.setFinalAmount(finalAmount);

        // Then
        assertAll(
                () -> assertEquals(bankReceiptNumber, paymentDetail.getBankReceiptNumber()),
                () -> assertEquals(originalAmount, paymentDetail.getOriginalAmount()),
                () -> assertEquals(finalAmount, paymentDetail.getFinalAmount())
        );
    }

    @Test
    void paymentDetail_WithAllArgsConstructor_ShouldSetAllFields() {
        // Given
        String bankReceiptNumber = "BANK123";
        double originalAmount = 100.0;
        double finalAmount = 90.0;
        List<String> appliedPromotions = List.of("PROMO1", "PROMO2");

        // When
        PaymentDetail paymentDetail = new PaymentDetail(bankReceiptNumber, originalAmount, finalAmount, appliedPromotions);

        // Then
        assertAll(
                () -> assertEquals(bankReceiptNumber, paymentDetail.getBankReceiptNumber()),
                () -> assertEquals(originalAmount, paymentDetail.getOriginalAmount()),
                () -> assertEquals(finalAmount, paymentDetail.getFinalAmount()),
                () -> assertEquals(appliedPromotions, paymentDetail.getAppliedPromotions())
        );
    }
}