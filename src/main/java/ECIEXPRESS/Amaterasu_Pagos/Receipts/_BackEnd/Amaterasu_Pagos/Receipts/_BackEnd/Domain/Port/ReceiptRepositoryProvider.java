package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;

import java.util.List;

public interface ReceiptRepositoryProvider {
    ReceiptRepositoryResponse save(Receipt receipt);
    List<ReceiptRepositoryResponse> getReceiptsByClientId(String clientId);
    ReceiptRepositoryResponse getByOrderId(String orderId);
    List<ReceiptRepositoryResponse> getAll();
}
