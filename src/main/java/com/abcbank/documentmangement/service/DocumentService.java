package com.abcbank.documentmangement.service;


import com.abcbank.documentmangement.entity.CustDocument;
import com.abcbank.documentmangement.exception.DocumentNotFoundException;
import com.abcbank.documentmangement.exception.NoDataFoundException;
import com.abcbank.documentmangement.mapper.CustDocumentMapper;
import com.abcbank.documentmangement.model.CustDocumentDTO;
import com.abcbank.documentmangement.model.PostResponse;
import com.abcbank.documentmangement.repository.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.aot.hint.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface DocumentService {
     public CustDocumentDTO saveDocument(CustDocumentDTO custDocumentDTO) ;

    public CustDocumentDTO getDocumentById(Long id) ;

    public List<CustDocumentDTO> getAllDocuments() ;

    public void deleteDocument(Long id) ;

    public void updateDocument(Long id, CustDocumentDTO updatedDocument) ;

    public PostResponse getAllDocuments(Integer pageNo, Integer pageSize);


}
