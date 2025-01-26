package com.abcbank.documentmangement.controller;

import com.abcbank.documentmangement.exception.DocumentNotFoundException;
import com.abcbank.documentmangement.exception.NotValidInputTypeException;
import com.abcbank.documentmangement.model.CustDocumentDTO;
import com.abcbank.documentmangement.model.DocumentMetaData;
import com.abcbank.documentmangement.model.PostResponse;
import com.abcbank.documentmangement.repository.DocumentRepository;
import com.abcbank.documentmangement.service.DocumentService;
import com.abcbank.documentmangement.utils.ApplicationConstants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api/documents")
class DocumentController {

    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String UPLOAD_SUCCESS_MESSAGE = "Document Uploaded successfully";
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @PostMapping(path = "/upload",consumes = {"multipart/form-data"})
    public ResponseEntity<String> createDocument(@RequestParam String userId,@RequestParam String postId , @RequestPart("file") MultipartFile document) throws IOException {
        String contentType = document.getContentType();
        // Validate the content type
        if (!PDF_CONTENT_TYPE.equals(contentType)) {
            throw new NotValidInputTypeException("Only PDF file will be allowed to upload");
        }
        CustDocumentDTO custDocumentDTO = new CustDocumentDTO();
        custDocumentDTO.setName(document.getOriginalFilename());
        custDocumentDTO.setDescription(document.getName());
        custDocumentDTO.setContent(document.getBytes());
        custDocumentDTO.setSize(document.getSize());
        custDocumentDTO.setContentType(document.getContentType());
        custDocumentDTO.setPostId(postId);
        custDocumentDTO.setUserId(userId);
        documentService.saveDocument(custDocumentDTO);

        return new ResponseEntity<>(UPLOAD_SUCCESS_MESSAGE, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustDocumentDTO> getDocument(@PathVariable Long id) {
        CustDocumentDTO document = documentService.getDocumentById(id);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

  /*  @GetMapping
    public ResponseEntity<List<CustDocumentDTO>> getAllDocuments() {
        return new ResponseEntity<>(documentService.getAllDocuments(), HttpStatus.OK);
    }*/

    @PutMapping("/{id}/document")
    public ResponseEntity<CustDocumentDTO> updateDocument(@PathVariable Long id, @RequestBody DocumentMetaData updatedDocument) throws Exception {
        CustDocumentDTO document = documentService.getDocumentById(id);
        if (document==null) {
            throw new DocumentNotFoundException(id);
        }
        document.setName(updatedDocument.getName());
        document.setUserId(updatedDocument.getUserId());
        document.setPostId(updatedDocument.getPostId());
        documentService.updateDocument(id, document);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllDocuments(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        PostResponse documentPage = documentService.getAllDocuments(page,size);
        return new ResponseEntity<>(documentPage, HttpStatus.OK);
    }


}


