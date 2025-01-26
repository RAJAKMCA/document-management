package com.abcbank.documentmanagement.controller;

import com.abcbank.documentmangement.controller.DocumentController;
import com.abcbank.documentmangement.exception.DocumentNotFoundException;
import com.abcbank.documentmangement.exception.NotValidInputTypeException;
import com.abcbank.documentmangement.model.CustDocumentDTO;
import com.abcbank.documentmangement.model.DocumentMetaData;
import com.abcbank.documentmangement.model.PostResponse;
import com.abcbank.documentmangement.service.DocumentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDocumentSuccessfully() throws IOException {
        Mockito.when(multipartFile.getContentType()).thenReturn("application/pdf");
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("test.pdf");
        Mockito.when(multipartFile.getName()).thenReturn("test");
        Mockito.when(multipartFile.getBytes()).thenReturn(new byte[0]);
        Mockito.when(multipartFile.getSize()).thenReturn(100L);

        ResponseEntity<String> response = documentController.createDocument("user1", "post1", multipartFile);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Document Uploaded successfully", response.getBody());
        Mockito.verify(documentService, Mockito.times(1)).saveDocument(ArgumentMatchers.any(CustDocumentDTO.class));
    }

    @Test
    void createDocumentWithInvalidContentType() throws IOException {
        Mockito.when(multipartFile.getContentType()).thenReturn("text/plain");

        try {
            documentController.createDocument("user1", "post1", multipartFile);
        } catch (NotValidInputTypeException e) {
            Assertions.assertEquals("Only PDF file will be allowed to upload", e.getMessage());
        }

        Mockito.verify(documentService, Mockito.never()).saveDocument(ArgumentMatchers.any(CustDocumentDTO.class));
    }

    @Test
    void getDocumentSuccessfully() {
        CustDocumentDTO document = new CustDocumentDTO();
        document.setDescription("1");
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(document);

        ResponseEntity<CustDocumentDTO> response = documentController.getDocument(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(document, response.getBody());
    }

    @Test
    void updateDocumentSuccessfully() throws Exception {
        CustDocumentDTO document = new CustDocumentDTO();
        DocumentMetaData updatedDocument = new DocumentMetaData();
        updatedDocument.setName("Updated Name");
        updatedDocument.setUserId("user1");
        updatedDocument.setPostId("post1");

        Mockito.when(documentService.getDocumentById(1L)).thenReturn(document);

        ResponseEntity<CustDocumentDTO> response = documentController.updateDocument(1L, updatedDocument);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(document, response.getBody());
        Mockito.verify(documentService, Mockito.times(1)).updateDocument(1L, document);
    }

    @Test
    void updateDocumentNotFound() {
        DocumentMetaData updatedDocument = new DocumentMetaData();
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(null);

        try {
            documentController.updateDocument(1L, updatedDocument);
        } catch (DocumentNotFoundException e) {
            Assertions.assertEquals("Document not found with id:1", e.getMessage());
        }catch (Exception e) {
            Assertions.fail("Unexpected exception thrown");
        }

        Mockito.verify(documentService, Mockito.never()).updateDocument(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CustDocumentDTO.class));
    }

    @Test
    void deleteDocumentSuccessfully() {
        ResponseEntity<Void> response = documentController.deleteDocument(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(documentService, Mockito.times(1)).deleteDocument(1L);
    }

    @Test
    void getAllDocumentsSuccessfully() {
        PostResponse postResponse = new PostResponse();
        Mockito.when(documentService.getAllDocuments(0, 10)).thenReturn(postResponse);

        ResponseEntity<PostResponse> response = documentController.getAllDocuments(0, 10);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(postResponse, response.getBody());
    }
}
