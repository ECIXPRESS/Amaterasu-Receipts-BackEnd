package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Clients.QRValidator;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.QrValidatorProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Clients.QRValidator.Dto.QRValidatorRequests.CreateQRValidatorRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Clients.QRValidator.Dto.QRValidatorResponses.CreateQRValidatorResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Mappers.QRMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class QRValidatorProviderAdapter implements QrValidatorProvider {
    private final RestTemplate restTemplate;

    @Value("${microservices.qr-validator.url}")
    private String baseUrl;

    @Value("${microservices.qr-validator.base-path}")
    private String basePath;

    @Override
    public CreateQRValidatorResponse createQRCode(Receipt receipt) {
        try {
                log.info("Creating QR Code for order: {}", receipt.getOrderId());
            CreateQRValidatorRequest createQRValidatorRequest = QRMapper.mapToCreateQRValidatorRequest(receipt);

            HttpHeaders headers = createHeaders();
            HttpEntity<CreateQRValidatorRequest> entity = new HttpEntity<>(createQRValidatorRequest, headers);

            ResponseEntity<CreateQRValidatorResponse> response = restTemplate.exchange(
                    baseUrl+basePath, HttpMethod.POST, entity, CreateQRValidatorResponse.class);

            CreateQRValidatorResponse receiptResponse = response.getBody();

            log.info("Creating response received for order {}", receipt.getOrderId());

            return receiptResponse;

        } catch (Exception e) {
            log.error("Error Creating QR code for order {} Error: {}",receipt.getOrderId(), e.getMessage());
            return new CreateQRValidatorResponse(null);
        }
    }
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
