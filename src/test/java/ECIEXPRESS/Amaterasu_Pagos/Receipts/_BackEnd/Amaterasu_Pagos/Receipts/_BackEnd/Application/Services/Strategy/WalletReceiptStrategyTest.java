package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.DateUtils;
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
class WalletReceiptStrategyTest {

    @Mock
    private ReceiptRepositoryProvider receiptRepositoryProvider;

    private WalletReceiptStrategy walletReceiptStrategy;

    @BeforeEach
    void setUp() {
        walletReceiptStrategy = new WalletReceiptStrategy(receiptRepositoryProvider);
    }

    @Test
    void createReceipt_WithValidWalletPayment_ShouldCreateReceiptSuccessfully() throws Exception {
        // Given
        Wallet wallet = new Wallet();
        wallet.setPaymentMethodType(PaymentMethodType.WALLET);

        TimeStamps timeStamps = new TimeStamps();
        // For WALLET payment, dates should be consistent (no specific order requirement like BANK/CASH)
        String currentDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setReceiptGeneratedDate(currentDate);
        timeStamps.setPaymentProcessedAt(currentDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, wallet, timeStamps, List.of()
        );

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId("order123");
        receiptDoc.setClientId("client123");
        receiptDoc.setStoreId("store456");
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING); // Wallet remains PENDING
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.save(any(Receipt.class))).thenReturn(repoResponse);

        // When
        CreateReceiptResponse response = walletReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        assertEquals("order123", response.orderId());
        assertEquals("client123", response.clientId());
        assertEquals(ReceiptStatus.PENDING, response.receiptStatus()); // Should be PENDING for wallet
        assertNotNull(response.qrCode());

        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }

    @Test
    void createReceipt_WithDeliveredStatus_ShouldCreateSuccessfully() {
        // Given
        Wallet wallet = new Wallet();
        wallet.setPaymentMethodType(PaymentMethodType.WALLET);

        TimeStamps timeStamps = new TimeStamps();
        String currentDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setReceiptGeneratedDate(currentDate);
        timeStamps.setPaymentProcessedAt(currentDate);

        // Use equal amounts to pass the amount validation
        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, wallet, timeStamps, List.of("WALLET5")
        );

        // Mock response
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);
        
        when(receiptRepositoryProvider.save(any(Receipt.class)))
            .thenReturn(new ReceiptRepositoryResponse(receiptDoc));

        // When
        CreateReceiptResponse response = walletReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }

    @Test
    void createReceipt_WithRefundedStatus_ShouldCreateSuccessfully() {
        // Given
        Wallet wallet = new Wallet();
        wallet.setPaymentMethodType(PaymentMethodType.WALLET);

        TimeStamps timeStamps = new TimeStamps();
        String currentDate = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setReceiptGeneratedDate(currentDate);
        timeStamps.setPaymentProcessedAt(currentDate);

        // Use equal amounts to pass the amount validation
        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, wallet, timeStamps, List.of("WALLET5")
        );

        // Mock response
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);
        
        when(receiptRepositoryProvider.save(any(Receipt.class)))
            .thenReturn(new ReceiptRepositoryResponse(receiptDoc));

        // When
        CreateReceiptResponse response = walletReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }
}