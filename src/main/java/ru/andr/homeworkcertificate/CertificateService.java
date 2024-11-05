package ru.andr.homeworkcertificate;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Gauge;

import java.security.cert.X509Certificate;
import java.util.List;
import java.util.function.Supplier;

@Service
public class CertificateService {
    private final Environment environment;
    private final MeterRegistry meterRegistry;

    public CertificateService(Environment environment, MeterRegistry meterRegistry) {
        this.environment = environment;
        this.meterRegistry = meterRegistry;
        registerCertificateMetrics();
    }

    // Регистрация метрики для сертификатов
    private void registerCertificateMetrics() {
        try {
            List<X509Certificate> certificates = KeyStoreUtils.loadCertificatesFromKeyStore(
                    environment.getProperty("storage.path"), environment.getProperty("password"), "JKS");

            for (X509Certificate certificate : certificates) {
                // Имя сертификата
                String certName = certificate.getSubjectX500Principal().getName();
                // Остаток времени до истечения (в днях)
                Supplier<Number> daysUntilExpiration = () -> getDaysUntilExpiration(certificate);

                // Создание метрики для сертификата с использованием Micrometer
                Gauge.builder("certificates.days_until_expiration", daysUntilExpiration)
                        .tag("certificate", certName)
                        .register(meterRegistry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Вычисление количества дней до истечения сертификата
    private long getDaysUntilExpiration(X509Certificate certificate) {
        long diffMillis = certificate.getNotAfter().getTime() - System.currentTimeMillis();
        return diffMillis > 0 ? diffMillis / (24 * 60 * 60 * 1000) : 0;  // если сертификат уже истек, возвращаем 0
    }
}
