package com.abcbank.documentmangement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DocumentMetaData {
    private String name;
    private String userId;
    private String postId;
}
