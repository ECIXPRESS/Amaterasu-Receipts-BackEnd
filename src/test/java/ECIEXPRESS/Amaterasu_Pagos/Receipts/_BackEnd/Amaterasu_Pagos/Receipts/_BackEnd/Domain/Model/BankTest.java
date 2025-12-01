package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.BankAccountType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.BankPaymentType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void bank_ShouldSetAndGetAllFields() {
        // Given
        Bank bank = new Bank();
        BankPaymentType paymentType = BankPaymentType.PSE;
        BankAccountType accountType = BankAccountType.SAVINGS_ACCOUNT;
        String receiptNumber = "BANK123";
        PaymentMethodType methodType = PaymentMethodType.BANK;

        // When
        bank.setBankPaymentType(paymentType);
        bank.setBankAccountType(accountType);
        bank.setBankReceiptNumber(receiptNumber);
        bank.setPaymentMethodType(methodType);

        // Then
        assertAll(
                () -> assertEquals(paymentType, bank.getBankPaymentType()),
                () -> assertEquals(accountType, bank.getBankAccountType()),
                () -> assertEquals(receiptNumber, bank.getBankReceiptNumber()),
                () -> assertEquals(methodType, bank.getPaymentMethodType())
        );
    }

    @Test
    void createPaymentMethod_ShouldReturnNewBankInstance() {
        // Given
        Bank bank = new Bank();

        // When
        PaymentMethod result = bank.createPaymentMethod();

        // Then
        assertNotNull(result);
        assertTrue(result instanceof Bank);
    }
}