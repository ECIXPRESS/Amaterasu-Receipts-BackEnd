package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Adapter;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Mappers.ReceiptDocumentMapper;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Repositories.MongoReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReceiptRepositoryAdapter implements ReceiptRepositoryProvider {
    private final MongoReceiptRepository mongoReceiptRepository;

    @Override
    public ReceiptRepositoryResponse save(Receipt receipt) {
        ReceiptDocument document = ReceiptDocumentMapper.toReceiptDocument(receipt);
        ReceiptDocument savedDocument = mongoReceiptRepository.save(document);
        return ReceiptDocumentMapper.toReceipt(savedDocument);
    }

    @Override
    public List<ReceiptRepositoryResponse> getReceiptsByClientId(String clientId) {
        List<ReceiptDocument> documents = mongoReceiptRepository.findByClientId(clientId);
        return documents.stream()
                .map(ReceiptDocumentMapper::toReceipt)
                .collect(Collectors.toList());
    }

    @Override
    public ReceiptRepositoryResponse getByOrderId(String orderId) {
        ReceiptDocument document = mongoReceiptRepository.findByOrderId(orderId);
        return ReceiptDocumentMapper.toReceipt(document);
    }

    @Override
    public List<ReceiptRepositoryResponse> getAll() {
        List<ReceiptDocument> documents = mongoReceiptRepository.findAll();
        return documents.stream()
                .map(ReceiptDocumentMapper::toReceipt)
                .collect(Collectors.toList());
    }
}
