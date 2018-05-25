package com.bht.banhuitong.security.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.xx.armory.commons.Validators;
import org.xx.armory.services.ServiceException;

public final class KeyUtils {
    private static final SecretKeyFactory DES_KEY_FACTORY = createDESKeyFactory();
    private static final KeyFactory RSA_KEY_FACTORY = createRSAKeyFactory();

    private KeyUtils() {
        throw new UnsupportedOperationException();
    }

    private static SecretKeyFactory createDESKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException var1) {
            throw new IllegalStateException("cannot get DES algorithm", var1);
        }
    }

    private static KeyFactory createRSAKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException var1) {
            throw new ServiceException("cannot get DES algorithm", var1);
        }
    }

    public static KeyPair generateRSAKeys() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        generator.initialize(2048, random);
        return generator.generateKeyPair();
    }

    public static SecretKey generateDESKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        generator.init(56, random);
        return generator.generateKey();
    }

    public static SecretKey buildDESKey(byte[] value) throws InvalidKeyException, InvalidKeySpecException {
        Validators.notEmpty(value, "value");
        return DES_KEY_FACTORY.generateSecret(new DESKeySpec(value));
    }

    public static PublicKey buildRSAPublicKey(byte[] value) throws InvalidKeyException, InvalidKeySpecException {
        Validators.notEmpty(value, "value");
        return RSA_KEY_FACTORY.generatePublic(new X509EncodedKeySpec(value));
    }

    public static PrivateKey buildRSAPrivateKey(byte[] value) throws InvalidKeyException, InvalidKeySpecException {
        Validators.notEmpty(value, "value");
        return RSA_KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(value));
    }

    private static String findLastKeyAlias(KeyStore keyStore) throws KeyStoreException {
        Validators.notNull(keyStore, "keyStore");
        Enumeration enu = keyStore.aliases();

        String alias;
        do {
            if (!enu.hasMoreElements()) {
                return null;
            }

            alias = (String)enu.nextElement();
        } while(!keyStore.isKeyEntry(alias));

        return alias;
    }

    public static KeyPair exportPCKS12Keys(InputStream stream, char[] password) {
        Validators.notNull(stream, "stream");
//        Validators.notEmpty(password, "password");

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, password);
            String alias = findLastKeyAlias(keyStore);
            if (alias == null) {
                throw new IllegalArgumentException("cannot find alias from stream");
            } else {
                PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, password);
                PublicKey publicKey = keyStore.getCertificate(alias).getPublicKey();
                return new KeyPair(publicKey, privateKey);
            }
        } catch (IOException | GeneralSecurityException var6) {
            throw new RuntimeException("cannot dump keys from PKCS12 cert", var6);
        }
    }

    public static String dumpPublicKey(PublicKey publicKey) {
        Validators.notNull(publicKey, "publicKey");
        return "algorithm: " + publicKey.getAlgorithm() + "\nformat: " + publicKey.getFormat() + "\nencoded: " + Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static PublicKey exportX509Keys(InputStream stream) {
        Validators.notNull(stream, "stream");

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate)cf.generateCertificate(stream);
            return cert.getPublicKey();
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException("cannot dump keys from X.509 cert", var3);
        }
    }

    public static String dumpPrivateKey(PrivateKey privateKey) {
        Validators.notNull(privateKey, "privateKey");
        return "algorithm: " + privateKey.getAlgorithm() + "\nformat: " + privateKey.getFormat() + "\nencoded: " + Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        if (args.length > 0) {
            fileName = StringUtils.trimToEmpty(args[0]);
        }

        while(fileName.isEmpty()) {
            System.out.print("input full certificate file path: ");
            fileName = StringUtils.trimToEmpty(scanner.nextLine());
        }

        String ext = StringUtils.trimToEmpty(FilenameUtils.getExtension(fileName).toLowerCase());
        byte var6 = -1;
        switch(ext.hashCode()) {
        case 98789:
            if (ext.equals("crt")) {
                var6 = 0;
            }
            break;
        case 109201:
            if (ext.equals("p12")) {
                var6 = 1;
            }
        }

        String result;
        InputStream stream = null;
        switch(var6) {
        case 0:
            stream = FileUtils.openInputStream(new File(fileName));
            Throwable var36 = null;

            try {
                result = dumpPublicKey(exportX509Keys(stream));
                break;
            } catch (Throwable var32) {
                var36 = var32;
                throw var32;
            } finally {
                if (stream != null) {
                    if (var36 != null) {
                        try {
                            stream.close();
                        } catch (Throwable var30) {
                            var36.addSuppressed(var30);
                        }
                    } else {
                        stream.close();
                    }
                }

            }
            
        case 1:
            String password = "";
            if (args.length > 1) {
                password = StringUtils.trimToEmpty(args[1]);
            }

            while(password.isEmpty()) {
                System.out.println("input password: ");
                password = StringUtils.trimToEmpty(scanner.nextLine());
            }

            stream = FileUtils.openInputStream(new File(fileName));
            Throwable var9 = null;

            try {
                KeyPair keyPair = exportPCKS12Keys(stream, password.toCharArray());
                result = dumpPublicKey(keyPair.getPublic()) + "\n" + dumpPrivateKey(keyPair.getPrivate());
                break;
            } catch (Throwable var31) {
                var9 = var31;
                throw var31;
            } finally {
                if (stream != null) {
                    if (var9 != null) {
                        try {
                            stream.close();
                        } catch (Throwable var29) {
                            var9.addSuppressed(var29);
                        }
                    } else {
                        stream.close();
                    }
                }

            }
        default:
            System.err.print("unknown cert file extension: " + ext);
            return;
        }

        System.out.println(result);
    }
}
