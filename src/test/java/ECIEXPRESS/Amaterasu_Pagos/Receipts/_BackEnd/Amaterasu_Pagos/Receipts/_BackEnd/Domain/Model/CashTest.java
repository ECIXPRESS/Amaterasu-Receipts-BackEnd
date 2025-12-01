package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashTest {

    @Test
    void cash_ShouldSetAndGetPaymentMethodType() {
        // Given
        Cash cash = new Cash();
        PaymentMethodType expectedType = PaymentMethodType.CASH;

        // When
        cash.setPaymentMethodType(expectedType);

        // Then
        assertEquals(expectedType, cash.getPaymentMethodType());
    }

    @Test
    void createPaymentMethod_ShouldReturnNewCashInstance() {
        // Given
        Cash cash = new Cash();

        // When
        PaymentMethod result = cash.createPaymentMethod();

        // Then
        assertNotNull(result);
        assertTrue(result instanceof Cash);
    }
}