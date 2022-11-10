package com.seed.careerhub.domain;

import lombok.Data;

@Data
public class NearNft {
    String nftAccountId;
    String tokenId;
    public NearNft(String nftAccountId, String tokenId) {
    }
}
