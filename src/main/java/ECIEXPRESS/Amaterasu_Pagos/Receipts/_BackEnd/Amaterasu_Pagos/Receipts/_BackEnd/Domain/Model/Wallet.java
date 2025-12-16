package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Wallet extends PaymentMethod {

    public PaymentMethod createPaymentMethod() {
        Wallet wallet = new Wallet();
        wallet.setPaymentMethodType(PaymentMethodType.WALLET);
        return wallet;
    }
}