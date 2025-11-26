package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceiptRepositoryProvider extends MongoRepository<ReceiptDocument, String> {
    ReceiptRepositoryResponse save(Receipt receipt);
}
