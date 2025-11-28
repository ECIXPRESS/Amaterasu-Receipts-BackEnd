package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Mappers;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;


public class ReceiptDocumentMapper {
    public static ReceiptDocument toReceiptDocument(Receipt receipt){
        ReceiptDocument receiptDocument = new ReceiptDocument();
        receiptDocument.setReceiptId(receipt.getReceiptId());
        receiptDocument.setOrderId(receipt.getOrderId());
        receiptDocument.setClientId(receipt.getClientId());
        receiptDocument.setStoreId(receipt.getStoreId());
        receiptDocument.setPaymentDetail(receipt.getPaymentDetail());
        receiptDocument.setPaymentMethod(receipt.getPaymentMethod());
        receiptDocument.setTimeStamps(receipt.getTimeStamps());
        return receiptDocument;
    }

    public static ReceiptRepositoryResponse toReceipt(ReceiptDocument receiptDocument){
        ReceiptRepositoryResponse receiptResponse = new ReceiptRepositoryResponse(receiptDocument);
        return receiptResponse;
    }
}
