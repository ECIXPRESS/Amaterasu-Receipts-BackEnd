package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void wallet_ShouldSetAndGetPaymentMethodType() {
        // Given
        Wallet wallet = new Wallet();
        PaymentMethodType expectedType = PaymentMethodType.WALLET;

        // When
        wallet.setPaymentMethodType(expectedType);

        // Then
        assertEquals(expectedType, wallet.getPaymentMethodType());
    }

    @Test
    void createPaymentMethod_ShouldReturnNewWalletInstance() {
        // Given
        Wallet wallet = new Wallet();

        // When
        PaymentMethod result = wallet.createPaymentMethod();

        // Then
        assertNotNull(result);
        assertTrue(result instanceof Wallet);
    }
}