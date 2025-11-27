package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CashReceiptStrategy implements ReceiptStrategy{
    private  ReceiptRepositoryProvider receiptRepositoryProvider;
    @Override
    public CreateReceiptResponse createReceipt(CreateReceiptRequest request) {
        log.info("Creating receipt for client {} For store {} with orderId {}", request.clientId(),request.storeId(), request.orderId());
        Receipt receipt = Receipt.createReceipt(request);
        log.info("Receipt created successfully");
        log.info("Saving receipt to database");
        ReceiptRepositoryResponse repositoryResponse= receiptRepositoryProvider.save(receipt);
        log.info("Receipt saved successfully");
        receipt.getTimeStamps().setReceiptGeneratedDate(new Date().toString());
        return null;
    }
}
