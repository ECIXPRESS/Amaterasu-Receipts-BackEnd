package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Bank;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BankReceiptStrategy extends AbstractReceiptStrategy {

    public BankReceiptStrategy(ReceiptRepositoryProvider receiptRepositoryProvider,
                               EncryptionUtil encryptionUtil) {
        super(receiptRepositoryProvider, encryptionUtil);
    }

    @Override
    public CreateReceiptResponse createReceipt(CreateReceiptRequest request) {
        return createReceiptBase(request, ReceiptStatus.PAYED, true);
    }

    @Override
    protected void updateBankReceiptNumber(Receipt receipt) {
        if (receipt.getPaymentMethod().getPaymentMethodType() == PaymentMethodType.BANK) {
            Bank bank = (Bank) receipt.getPaymentMethod();
            receipt.getPaymentDetail().setBankReceiptNumber(bank.getBankReceiptNumber());
        }
    }
}