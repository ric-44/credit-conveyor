package ru.ashabelskii.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ashabelskii.deal.api.DocumentApi;
import ru.ashabelskii.deal.audit.Audit;
import ru.ashabelskii.deal.audit.model.AuditEventType;
import ru.ashabelskii.deal.service.DocumentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {

    private final DocumentService documentService;

    @Override
    @Audit(eventType = AuditEventType.DOCUMENT_SEND_DOCUMENTS)
    public ResponseEntity<Void> sendDocuments(UUID applicationId, HttpHeaders headers) {
        documentService.sendDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    @Audit(eventType = AuditEventType.DOCUMENT_SIGN_DOCUMENTS)
    public ResponseEntity<Void> signDocuments(UUID applicationId, HttpHeaders headers) {
        documentService.signDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    @Audit(eventType = AuditEventType.DOCUMENT_VERIFY_CODE)
    public ResponseEntity<Void> verifyCode(UUID applicationId, Integer sesCode, HttpHeaders headers) {
        documentService.verifyCode(applicationId, sesCode);
        return ResponseEntity.ok().build();
    }
}
