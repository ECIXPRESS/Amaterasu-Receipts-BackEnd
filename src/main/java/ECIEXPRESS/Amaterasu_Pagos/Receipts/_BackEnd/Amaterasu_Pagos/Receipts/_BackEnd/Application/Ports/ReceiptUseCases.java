package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Ports;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;

import java.util.List;

public interface ReceiptUseCases {
    CreateReceiptResponse createReceipt(CreateReceiptRequest request);

    List<GetReceiptResponse> getReceiptsByClientId(GetReceiptByClientIdRequest request);

    GetReceiptResponse getReceiptByOrderId(GetReceiptByOrderIdRequest request);

    GetQrReceiptResponse getQrCodeByOrderId(GetQrReceiptRequest request);

    boolean updateToPayed(UpdateToPayedRequest request);

    boolean updateToDelivered(UpdateToDeliveredRequest request);
}
