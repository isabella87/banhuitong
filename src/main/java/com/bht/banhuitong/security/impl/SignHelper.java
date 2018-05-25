package com.bht.banhuitong.security.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.xx.armory.commons.Converter;
import org.xx.armory.commons.Validators;

public final class SignHelper {
    private static final String DEFAULT_SIGN_ALGORITHM = "SHA1withRSA";
    private static final int DEFAULT_MAX_DIGEST_BLOCK_SIZE = 512;
    private final Logger logger = Logger.getLogger(SignHelper.class);
    private final Signature signature;
    private final int maxDigestBlockSize;

    private SignHelper(String algorithm, int maxDigestBlockSize) throws NoSuchAlgorithmException {
        algorithm = Validators.notBlank(algorithm, "algorithm").trim().toUpperCase();
        this.signature = Signature.getInstance(algorithm);
        this.maxDigestBlockSize = Validators.greaterThanOrEqual(maxDigestBlockSize, "maxDigestBlockSize", 512);
    }

    public static SignHelper getInstance() {
        try {
            return getInstance(DEFAULT_SIGN_ALGORITHM, DEFAULT_MAX_DIGEST_BLOCK_SIZE);
        } catch (NoSuchAlgorithmException var1) {
            throw new RuntimeException("cannot find algorithm: SHA1withRSA", var1);
        }
    }

    public static SignHelper getInstance(String algorithm) throws NoSuchAlgorithmException {
        return getInstance(algorithm, 16);
    }

    public static SignHelper getInstance(String algorithm, int maxDigestBlockSize) throws NoSuchAlgorithmException {
        return new SignHelper(algorithm, maxDigestBlockSize);
    }

    public boolean verifyHex(String content, String signature, PublicKey publicKey) throws InvalidKeyException, SignatureException {
        Validators.notNull(publicKey, "publicKey");
        this.logger.debug("verify: \"{"+content+"}\"\n with signature(hex): \"{"+signature+"}\"");
        return this.verify(content, StandardCharsets.UTF_8, Converter.toBytes(signature), publicKey);
    }

    public boolean verifyBase64(String content, String signature, PublicKey publicKey) throws InvalidKeyException, SignatureException {
        Validators.notNull(publicKey, "publicKey");
        this.logger.debug("verify: \"{"+content+"}\"\n with signature(hex): \"{"+signature+"}\"");
        return this.verify(content, StandardCharsets.UTF_8, Base64.getDecoder().decode(signature), publicKey);
    }

    public boolean verify(String content, Charset charset, byte[] signature, PublicKey publicKey) throws InvalidKeyException, SignatureException {
        Validators.notNull(charset, "charset");
        Validators.notNull(publicKey, "publicKey");
        if (StringUtils.isEmpty(content)) {
            return true;
        } else {
            this.signature.initVerify(publicKey);
            int p = 0;
            int l = content.length();

            int pe;
            for(int lr = l - this.maxDigestBlockSize; p < lr; p = pe) {
                pe = p + this.maxDigestBlockSize;
                this.signature.update(content.substring(p, pe).getBytes(charset));
            }

            if (p < l) {
                this.signature.update(content.substring(p).getBytes(charset));
            }

            return this.signature.verify(signature);
        }
    }

    public String signBase64(String content, PrivateKey privateKey) throws InvalidKeyException, SignatureException {
        Validators.notNull(privateKey, "privateKey");
        this.logger.debug("sign: \"{"+content+"}\"\n with private key");
        return Base64.getEncoder().encodeToString(this.sign(content, StandardCharsets.UTF_8, privateKey));
    }

    public byte[] sign(String content, Charset charset, PrivateKey privateKey) throws InvalidKeyException, SignatureException {
        Validators.notNull(charset, "charset");
        Validators.notNull(privateKey, "privateKey");
        if (StringUtils.isEmpty(content)) {
            return new byte[0];
        } else {
            this.signature.initSign(privateKey);
            int p = 0;
            int l = content.length();

            int pe;
            for(int lr = l - this.maxDigestBlockSize; p < lr; p = pe) {
                pe = p + this.maxDigestBlockSize;
                this.signature.update(content.substring(p, pe).getBytes(charset));
            }

            if (p < l) {
                this.signature.update(content.substring(p).getBytes(charset));
            }

            return this.signature.sign();
        }
    }

}
