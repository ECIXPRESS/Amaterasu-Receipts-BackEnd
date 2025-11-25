package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.BankAccountType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.BankPaymentType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Bank extends PaymentMethod {
    private PaymentMethodType paymentMethodType;
    private BankPaymentType bankPaymentType;
    private BankAccountType bankAccountType;
    private String bankReceiptNumber;
    private String bankName;

    public PaymentMethod createPaymentMethod() {
        Bank bank = new Bank();
        bank.setPaymentMethodType(PaymentMethodType.BANK);
        return bank;
    }

}
