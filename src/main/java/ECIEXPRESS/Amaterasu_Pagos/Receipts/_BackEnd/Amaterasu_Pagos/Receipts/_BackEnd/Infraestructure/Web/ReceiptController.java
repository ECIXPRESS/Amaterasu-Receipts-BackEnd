package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Ports.ReceiptUseCases;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
public class ReceiptController {

    private final ReceiptUseCases receiptUseCases;

    @PostMapping
    public ResponseEntity<CreateReceiptResponse> createReceipt(@RequestBody CreateReceiptRequest request) {
        CreateReceiptResponse response = receiptUseCases.createReceipt(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<GetReceiptResponse>> getReceiptsByClientId(
            @PathVariable String clientId) {
        
        GetReceiptByClientIdRequest request = new GetReceiptByClientIdRequest(clientId);
        List<GetReceiptResponse> response = receiptUseCases.getReceiptsByClientId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<GetReceiptResponse> getReceiptByOrderId(@PathVariable String orderId) {
        GetReceiptByOrderIdRequest request = new GetReceiptByOrderIdRequest(orderId);
        GetReceiptResponse response = receiptUseCases.getReceiptByOrderId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}/qr")
    public ResponseEntity<GetQrReceiptResponse> getQrCodeByOrderId(@PathVariable String orderId) {
        GetQrReceiptRequest request = new GetQrReceiptRequest(orderId);
        GetQrReceiptResponse response = receiptUseCases.getQrCodeByOrderId(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<Void> updateToPayed(@PathVariable String orderId) {
        UpdateToPayedRequest request = new UpdateToPayedRequest(orderId);
        boolean updated = receiptUseCases.updateToPayed(request);
        return updated ? 
            ResponseEntity.ok().build() : 
            ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<Void> updateToDelivered(@PathVariable String orderId){
        UpdateToDeliveredRequest request = new UpdateToDeliveredRequest(orderId);
        boolean updated = receiptUseCases.updateToDelivered(request);
        return updated ? 
            ResponseEntity.ok().build() : 
            ResponseEntity.badRequest().build();
    }
}
