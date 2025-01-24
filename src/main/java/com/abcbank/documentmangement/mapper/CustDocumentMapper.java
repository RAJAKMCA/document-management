package com.abcbank.documentmangement.mapper;


import com.abcbank.documentmangement.entity.CustDocument;
import com.abcbank.documentmangement.model.CustDocumentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustDocumentMapper {
    private ModelMapper modelMapper=new ModelMapper();

    public CustDocumentDTO toDto(CustDocument custDocument) {
        return modelMapper.map(custDocument, CustDocumentDTO.class);
    }

    public List<CustDocumentDTO> toDtoList(List<CustDocument> custDocumentList) {
        return custDocumentList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public CustDocument fromDto(CustDocumentDTO custDocumentDTO) {
        return modelMapper.map(custDocumentDTO, CustDocument.class);
    }

}