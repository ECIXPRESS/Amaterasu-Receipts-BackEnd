package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Adapter.ReceiptRepositoryAdapter;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Repositories.MongoReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptRepositoryAdapterTest {

    @Mock
    private MongoReceiptRepository mongoReceiptRepository;

    private ReceiptRepositoryAdapter receiptRepositoryAdapter;

    @BeforeEach
    void setUp() {
        receiptRepositoryAdapter = new ReceiptRepositoryAdapter(mongoReceiptRepository);
    }

    @Test
    void save_ShouldSaveAndReturnResponse() {
        // Given
        Receipt receipt = createSampleReceipt();
        ReceiptDocument document = createSampleReceiptDocument();

        when(mongoReceiptRepository.save(any(ReceiptDocument.class))).thenReturn(document);

        // When
        ReceiptRepositoryResponse result = receiptRepositoryAdapter.save(receipt);

        // Then
        assertNotNull(result);
        assertNotNull(result.receiptDocument());
        assertEquals("receipt123", result.receiptDocument().getReceiptId());
        verify(mongoReceiptRepository).save(any(ReceiptDocument.class));
    }

    @Test
    void getReceiptsByClientId_ShouldReturnReceipts() {
        // Given
        String clientId = "client123";
        List<ReceiptDocument> documents = Arrays.asList(
                createSampleReceiptDocument(),
                createSampleReceiptDocument()
        );

        when(mongoReceiptRepository.findByClientId(clientId)).thenReturn(documents);

        // When
        List<ReceiptRepositoryResponse> result = receiptRepositoryAdapter.getReceiptsByClientId(clientId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mongoReceiptRepository).findByClientId(clientId);
    }

    @Test
    void getByOrderId_ShouldReturnReceipt() {
        // Given
        String orderId = "order123";
        ReceiptDocument document = createSampleReceiptDocument();

        when(mongoReceiptRepository.findByOrderId(orderId)).thenReturn(document);

        // When
        ReceiptRepositoryResponse result = receiptRepositoryAdapter.getByOrderId(orderId);

        // Then
        assertNotNull(result);
        assertNotNull(result.receiptDocument());
        assertEquals("receipt123", result.receiptDocument().getReceiptId());
        verify(mongoReceiptRepository).findByOrderId(orderId);
    }

    @Test
    void getAll_ShouldReturnAllReceipts() {
        // Given
        List<ReceiptDocument> documents = Arrays.asList(
                createSampleReceiptDocument(),
                createSampleReceiptDocument()
        );

        when(mongoReceiptRepository.findAll()).thenReturn(documents);

        // When
        List<ReceiptRepositoryResponse> result = receiptRepositoryAdapter.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mongoReceiptRepository).findAll();
    }

    private Receipt createSampleReceipt() {
        Receipt receipt = new Receipt();
        receipt.setReceiptId("receipt123");
        receipt.setOrderId("order123");
        receipt.setClientId("client123");
        receipt.setStoreId("store456");
        receipt.setReceiptStatus(ReceiptStatus.PENDING);
        receipt.setOrderStatus(OrderStatus.PENDING);
        receipt.setPaymentDetail(new PaymentDetail());
        receipt.setPaymentMethod(new Cash());
        receipt.setTimeStamps(new TimeStamps());
        return receipt;
    }

    private ReceiptDocument createSampleReceiptDocument() {
        ReceiptDocument document = new ReceiptDocument();
        document.setReceiptId("receipt123");
        document.setOrderId("order123");
        document.setClientId("client123");
        document.setStoreId("store456");
        document.setReceiptStatus(ReceiptStatus.PENDING);
        document.setOrderStatus(OrderStatus.PENDING);
        document.setPaymentDetail(new PaymentDetail());
        document.setPaymentMethod(new Cash());
        document.setTimeStamps(new TimeStamps());
        return document;
    }
}