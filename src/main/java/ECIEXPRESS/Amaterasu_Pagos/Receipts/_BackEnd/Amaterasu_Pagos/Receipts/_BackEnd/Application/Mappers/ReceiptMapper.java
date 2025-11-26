package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Mappers;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
public class ReceiptMapper {
    public static CreateReceiptResponse receiptToCreateReceiptResponse(Receipt receipt){
        return new CreateReceiptResponse(
                receipt.getReceiptId(),
                receipt.getOrderId(),
                receipt.getClientId(),
                receipt.getStoreId(),
                receipt.getPaymentDetail().getFinalAmount(),
                receipt.getReceiptStatus()
        );
    }

    public static Receipt createReceipt(ReceiptRepositoryResponse response){
        return new Receipt(
                response.receiptDocument().getReceiptId(),
                response.receiptDocument().getOrderId(),
                response.receiptDocument().getClientId(),
                response.receiptDocument().getStoreId(),
                response.receiptDocument().getPaymentDetail(),
                response.receiptDocument().getPaymentMethod(),
                response.receiptDocument().getReceiptStatus(),
                response.receiptDocument().getTimeStamps()
        );
    }
}
