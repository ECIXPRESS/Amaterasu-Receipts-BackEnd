package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Mappers.ReceiptMapper;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.QRCode;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.DateUtils;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractReceiptStrategy implements ReceiptStrategy {

    protected final ReceiptRepositoryProvider receiptRepositoryProvider;
    protected final EncryptionUtil encryptionUtil;

    protected CreateReceiptResponse createReceiptBase(CreateReceiptRequest request,
                                                      ReceiptStatus status,
                                                      boolean updateBankReceiptNumber) {
        log.info("Creating receipt for client {} For store {} with orderId {}",
                request.clientId(), request.storeId(), request.orderId());

        Receipt receipt = Receipt.createReceipt(request);
        log.info("Receipt created successfully");

        receipt.getTimeStamps().setReceiptGeneratedDate(
                DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT));

        String qrCode = generateQrCode(receipt);

        if (updateBankReceiptNumber) {
            updateBankReceiptNumber(receipt);
        }

        log.info("Saving receipt to database");
        receiptRepositoryProvider.save(receipt);
        log.info("Receipt saved successfully");

        receipt.setReceiptStatus(status);

        return ReceiptMapper.receiptToCreateReceiptResponse(receipt, qrCode);
    }

    private String generateQrCode(Receipt receipt) {
        log.info("Validating QR Code to be created");
        QRCode qr = new QRCode();
        try {
            String qrData = qr.createQrCode(receipt);
            return encryptionUtil.encrypt(qrData);
        } catch (Exception e) {
            log.error("Failed to validate QR Code because: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void updateBankReceiptNumber(Receipt receipt) {
        // Implementación por defecto vacía
        // Sobreescribir en BankReceiptStrategy si es necesario
    }

    @Override
    public abstract CreateReceiptResponse createReceipt(CreateReceiptRequest request);
}