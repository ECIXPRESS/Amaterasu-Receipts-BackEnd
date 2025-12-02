package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Repositories;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoReceiptRepository extends MongoRepository<ReceiptDocument, String> {
    @Query("{ 'clientId': ?0 }")
    List<ReceiptDocument> findByClientId(String clientId);
    @Query("{ 'orderId': ?0 }")
    ReceiptDocument findByOrderId(String orderId);
}

