package ru.andr.homeworkcertificate;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class KeyStoreUtils {

    // Загрузка сертификатов из KeyStore
    public static List<X509Certificate> loadCertificatesFromKeyStore(String keyStorePath, String keyStorePassword, String keyStoreType) throws Exception {
        // Загрузка KeyStore
        FileInputStream keyStoreFile = new FileInputStream(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());

        // Получаем все alias в KeyStore
        Enumeration<String> aliases = keyStore.aliases();
        List<X509Certificate> certificates = new ArrayList<>();

        // Извлекаем сертификаты по alias
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            if (certificate != null) {
                certificates.add(certificate);
            }
        }
        return certificates;
    }
}
