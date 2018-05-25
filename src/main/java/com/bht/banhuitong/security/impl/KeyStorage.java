package com.bht.banhuitong.security.impl;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.xx.armory.commons.Validators;

import com.bht.banhuitong.config.Configuration;

public class KeyStorage {
	private static final KeyStorage INSTANCE = new KeyStorage();
    private final Map<String, Key> allKeys = new ConcurrentHashMap<String, Key>();

    private KeyStorage() {
    }

    public static KeyStorage getInstance() {
        return INSTANCE;
    }

    private byte[] loadKeyBytes(String name) {
    	return Configuration.getBytes(name);
    }

    public SecretKey loadDESKey(String name) {
        try {
            return KeyUtils.buildDESKey(this.loadKeyBytes(name));
        } catch (GeneralSecurityException var3) {
            throw new IllegalStateException("cannot load DES key from <root/security/key[name=" + name + "]>");
        }
    }

    public PublicKey loadRSAPublicKey(String name) {
        try {
            return KeyUtils.buildRSAPublicKey(this.loadKeyBytes(name));
        } catch (GeneralSecurityException var3) {
            throw new IllegalStateException("cannot load RSA public key from <root/security/key[name=" + name + "]>");
        }
    }

    public PrivateKey loadRSAPrivateKey(String name) {
        try {
            return KeyUtils.buildRSAPrivateKey(this.loadKeyBytes(name));
        } catch (GeneralSecurityException var3) {
            throw new IllegalStateException("cannot load RSA private key from <root/security/key[name=" + name + "]>");
        }
    }

    /*public SecretKey getDESKey(String name) {
        name = Validators.notBlank(name, "name").trim();
        return (SecretKey)SecretKey.class.cast(this.allKeys.computeIfAbsent(name, (Function<? super String, ? extends Key>) loadDESKey(name)));
    }

    public PublicKey getRSAPublicKey(String name) {
        name = Validators.notBlank(name, "name").trim();
        return (PublicKey)PublicKey.class.cast(this.allKeys.computeIfAbsent(name,(Function<? super String, ? extends Key>) loadRSAPublicKey(name)));
    }

    public PrivateKey getRSAPrivateKey(String name) {
        name = Validators.notBlank(name, "name").trim();
        return (PrivateKey)PrivateKey.class.cast(this.allKeys.computeIfAbsent(name,(Function<? super String, ? extends Key>) loadRSAPrivateKey(name)));
    }*/
}
