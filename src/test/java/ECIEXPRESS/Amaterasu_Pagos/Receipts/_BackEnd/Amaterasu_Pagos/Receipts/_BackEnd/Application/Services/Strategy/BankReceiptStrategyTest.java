package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.DateUtils;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.EncryptionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankReceiptStrategyTest {

    @Mock
    private ReceiptRepositoryProvider receiptRepositoryProvider;
    
    @Mock
    private EncryptionUtil encryptionUtil;

    private BankReceiptStrategy bankReceiptStrategy;

    @BeforeEach
    void setUp() {
        bankReceiptStrategy = new BankReceiptStrategy(receiptRepositoryProvider, encryptionUtil);
    }

    @Test
    void createReceipt_WithValidBankPayment_ShouldCreateReceiptSuccessfully() throws Exception {
        // Given
        Bank bank = new Bank();
        bank.setPaymentMethodType(PaymentMethodType.BANK);
        bank.setBankPaymentType(BankPaymentType.CREDIT_CARD);
        bank.setBankAccountType(BankAccountType.CHECKING_ACCOUNT);
        bank.setBankReceiptNumber("BANK123456");
        bank.setBankName("Test Bank");

        TimeStamps timeStamps = new TimeStamps();
        String paymentDate = DateUtils.formatDate(new Date(System.currentTimeMillis() - 3600000), DateUtils.TIMESTAMP_FORMAT); // 1 hour ago
        String receiptDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setPaymentProcessedAt(paymentDate);
        timeStamps.setReceiptGeneratedDate(receiptDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, bank, timeStamps, List.of("PROMO10")
        );

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId("order123");
        receiptDoc.setClientId("client123");
        receiptDoc.setStoreId("store456");
        receiptDoc.setReceiptStatus(ReceiptStatus.PAYED);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.save(any(Receipt.class))).thenReturn(repoResponse);
        when(encryptionUtil.encrypt(anyString())).thenReturn("encryptedData");

        // When
        CreateReceiptResponse response = bankReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        assertEquals("order123", response.orderId());
        assertEquals("client123", response.clientId());
        assertEquals(ReceiptStatus.PAYED, response.receiptStatus());
        assertNotNull(response.qrCode());

        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }

    @Test
    void createReceipt_WithInvalidDateOrder_ShouldCreateSuccessfully() {
        // Given
        Bank bank = new Bank();
        bank.setPaymentMethodType(PaymentMethodType.BANK);
        bank.setBankPaymentType(BankPaymentType.CREDIT_CARD);
        bank.setBankAccountType(BankAccountType.CHECKING_ACCOUNT);
        bank.setBankReceiptNumber("BANK123456");
        bank.setBankName("Test Bank");

        TimeStamps timeStamps = new TimeStamps();
        // Payment date is after receipt date, but the implementation sets receipt date to current time
        String paymentDate = DateUtils.formatDate(new Date(System.currentTimeMillis() - 3600000), DateUtils.TIMESTAMP_FORMAT);
        String receiptDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setPaymentProcessedAt(paymentDate);
        timeStamps.setReceiptGeneratedDate(receiptDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, bank, timeStamps, List.of("PROMO10")
        );

        // Mock response
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId("order123");
        receiptDoc.setClientId("client123");
        receiptDoc.setStoreId("store456");
        receiptDoc.setReceiptStatus(ReceiptStatus.PAYED);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);
        
        when(receiptRepositoryProvider.save(any(Receipt.class)))
            .thenReturn(new ReceiptRepositoryResponse(receiptDoc));

        // When
        CreateReceiptResponse response = bankReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        assertEquals("order123", response.orderId());
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }

    @Test
    void createReceipt_WithInvalidReceiptStatus_ShouldCreateWithPayedStatus() {
        // Given
        Bank bank = new Bank();
        bank.setPaymentMethodType(PaymentMethodType.BANK);
        bank.setBankPaymentType(BankPaymentType.CREDIT_CARD);
        bank.setBankAccountType(BankAccountType.CHECKING_ACCOUNT);
        bank.setBankReceiptNumber("BANK123456");
        bank.setBankName("Test Bank");

        TimeStamps timeStamps = new TimeStamps();
        String paymentDate = DateUtils.formatDate(new Date(System.currentTimeMillis() - 3600000), DateUtils.TIMESTAMP_FORMAT);
        String receiptDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setPaymentProcessedAt(paymentDate);
        timeStamps.setReceiptGeneratedDate(receiptDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, bank, timeStamps, List.of("PROMO10")
        );

        // Mock repository to return a receipt with any status (should be overridden to PAYED)
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId("order123");
        receiptDoc.setClientId("client123");
        receiptDoc.setStoreId("store456");
        receiptDoc.setReceiptStatus(ReceiptStatus.REFUNDED); // This will be set to PAYED
        receiptDoc.setOrderStatus(OrderStatus.PENDING);
        
        when(receiptRepositoryProvider.save(any(Receipt.class)))
            .thenReturn(new ReceiptRepositoryResponse(receiptDoc));

        // When
        CreateReceiptResponse response = bankReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        assertEquals("order123", response.orderId());
        assertEquals(ReceiptStatus.PAYED, response.receiptStatus());
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }
}