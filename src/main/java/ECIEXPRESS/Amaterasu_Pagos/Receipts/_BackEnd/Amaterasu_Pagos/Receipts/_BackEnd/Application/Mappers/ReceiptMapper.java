package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Mappers;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.QRCode;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;

import java.util.List;

public class ReceiptMapper {
    public static CreateReceiptResponse receiptToCreateReceiptResponse(Receipt receipt, String qrCode){
        return new CreateReceiptResponse(
                receipt.getReceiptId(),
                receipt.getOrderId(),
                receipt.getClientId(),
                receipt.getStoreId(),
                receipt.getPaymentDetail().getFinalAmount(),
                receipt.getReceiptStatus(),
                qrCode
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
                response.receiptDocument().getOrderStatus(),
                response.receiptDocument().getTimeStamps()
        );
    }

    public static GetReceiptResponse receiptToGetReceiptResponse(Receipt receipt){
        return new GetReceiptResponse(
                receipt.getReceiptId(),
                receipt.getOrderId(),
                receipt.getClientId(),
                receipt.getStoreId(),
                receipt.getPaymentDetail(),
                receipt.getPaymentMethod(),
                receipt.getReceiptStatus(),
                receipt.getOrderStatus(),
                receipt.getTimeStamps()
        );
    }

    public static List<GetReceiptResponse> receiptDocumentToGetReceiptResponse(List<ReceiptDocument> receipts) {
        return receipts.stream().map(receipt -> new GetReceiptResponse(
                receipt.getReceiptId(),
                receipt.getOrderId(),
                receipt.getClientId(),
                receipt.getStoreId(),
                receipt.getPaymentDetail(),
                receipt.getPaymentMethod(),
                receipt.getReceiptStatus(),
                receipt.getOrderStatus(),
                receipt.getTimeStamps()
        )).toList();
    }

    public static GetQrReceiptResponse receiptToGetQrReceiptResponse(Receipt receipt) throws Exception {
        QRCode qr = new QRCode();
        return new GetQrReceiptResponse(qr.createQrCode(receipt));
    }
}
