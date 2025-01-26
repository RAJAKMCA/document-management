package com.abcbank.documentmangement.service.impl;

import com.abcbank.documentmangement.entity.CustDocument;
import com.abcbank.documentmangement.exception.DocumentNotFoundException;
import com.abcbank.documentmangement.exception.NoDataFoundException;
import com.abcbank.documentmangement.mapper.CustDocumentMapper;
import com.abcbank.documentmangement.model.CustDocumentDTO;
import com.abcbank.documentmangement.model.PostResponse;
import com.abcbank.documentmangement.repository.DocumentRepository;
import com.abcbank.documentmangement.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    private CustDocumentMapper mapper = new CustDocumentMapper();

    @Override
    public CustDocumentDTO saveDocument(CustDocumentDTO custDocumentDTO) {

        CustDocument custDocument = mapper.fromDto(custDocumentDTO);
        documentRepository.save(custDocument);
        return custDocumentDTO;
    }

    @Override
    public CustDocumentDTO getDocumentById(Long id) {

        Optional<CustDocument> customDocument = documentRepository.findById(id);
        CustDocumentDTO customDocumentDto = null;
        if (customDocument.isPresent()) {
            customDocumentDto = mapper.toDto(customDocument.get());
        } else {
            throw new DocumentNotFoundException(id);
        }
        return customDocumentDto;

    }

    @Override
    public List<CustDocumentDTO> getAllDocuments() {
        List<CustDocument> custDocumentList = documentRepository.findAll();
        if (custDocumentList.isEmpty())
            throw new NoDataFoundException();
        List<CustDocumentDTO> custDocumentDTOList = mapper.toDtoList(custDocumentList);
        return custDocumentDTOList;
    }

    @Override
    public void deleteDocument(Long id) {
        Optional<CustDocument> customDocument = documentRepository.findById(id);
        if (!customDocument.isPresent())
            throw new DocumentNotFoundException(id);

        documentRepository.deleteById(id);
    }

    @Override
    public void updateDocument(Long id, CustDocumentDTO updatedDocument) {
        documentRepository.save(mapper.fromDto(updatedDocument));
    }

    @Override
    public PostResponse getAllDocuments(Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<CustDocument> posts = documentRepository.findAll(pageable);

        // get content for page object
        List<CustDocument> listOfPosts = posts.getContent();
        List<CustDocumentDTO> content= listOfPosts.stream().map(post -> mapper.toDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }


}
