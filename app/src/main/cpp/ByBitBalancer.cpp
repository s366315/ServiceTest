#include "ByBitBalancer.h"
#include <QDebug>
#include <QWebSocket>
#include <QByteArray>

QT_USE_NAMESPACE

ByBitBalancer::ByBitBalancer(QObject *parent): QObject(parent)
{
    connect(&m_webSocket, &QWebSocket::connected, this, &ByBitBalancer::onConnected);
    connect(&m_webSocket, &QWebSocket::disconnected, this, &ByBitBalancer::onDisconnected);
    connect(&m_webSocket, QOverload<const QList<QSslError>&>::of(&QWebSocket::sslErrors), this, &ByBitBalancer::onSslErrors);
    connect(&m_webSocket, &QWebSocket::errorOccurred, this, &ByBitBalancer::onError);
}

void ByBitBalancer::open()
{
    qDebug() << "[ByBitBalancer::open] open";

    qDebug() << "Device supports OpenSSL: " << QSslSocket::supportsSsl();

    QSslConfiguration sslConfiguration;
    QString certificateString = "-----BEGIN CERTIFICATE-----\n"
                                "MIIFpzCCA4+gAwIBAgICEAAwDQYJKoZIhvcNAQEMBQAwdjELMAkGA1UEBhMCUlUx\n"
                                "GzAZBgNVBAgTElRhdGFyc3RhbiBSZXB1YmxpYzESMBAGA1UEChMJQVRPTSBJbmMu\n"
                                "MQswCQYDVQQLEwJJVDEpMCcGA1UEAxMgQVRPTSBJbmMuIEludGVybWVkaWF0ZSBD\n"
                                "QSAoTVIgMSkwHhcNMjQwNDEwMTE1NjMyWhcNMjQwNzA5MTE1NjMyWjBtMQswCQYD\n"
                                "VQQGEwJSVTEMMAoGA1UECBMDU1BiMQwwCgYDVQQHEwNTUGIxDTALBgNVBAoTBEF0\n"
                                "b20xDjAMBgNVBAsTBURldmVsMSMwIQYDVQQDDBpldWdlbmUua29ub25lbmtvQGF0\n"
                                "b20udGVhbTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBALdsg8/SJcBk\n"
                                "rebgw6iD5R1OtRFoUKUUwvAz/SQ01lTAbEaSuGyCGGI1fbiXFCQeX/WWFHYewtk3\n"
                                "L9BmgwD8zLC77+pAEvKuOOr/EIUF9K55wZYrK2CjsRULBhZ+oaJRfdoyxNe0jNKB\n"
                                "N342CO9bnS6lXZpKDT2zGRD1QN0t928dNHFISN6DloPQo1Ye4Bu74BlAr/vwawbs\n"
                                "wxhFmMneusN1t/t0ImYa7ZQupWQPb1XeXlH6is/RqN0aMQyrFjLKWyPXycJfJZzL\n"
                                "TInV3gumZCYnJjj7siO4DoZEPu9zEVSfsebdjmY4+MKQ4Wdt8s0dqCFGn2SjUz0d\n"
                                "P4vc61zYuyx/ouqwzBVOUysiq90P8nChgI2xhdRkpHCwiyuBgagwwCQrECMFwCUk\n"
                                "1benTdVGT0jmna7UJX83LMcB3SVvME07knd4cUuWbBOF1/ybkCN9pTBkQiPAM1Kf\n"
                                "Mf1aOXUajWQevCiypA+Td/+X+KmMHTPu6yEac9EqGxDHyJNI1/iUs6BS1gFtNYRU\n"
                                "+FZvIn3R0Sis9eE72SPMm+2xrL2vM5O4jatpEmIq/pZvVqHkj0HFF11BvlgPcoDp\n"
                                "M8o51A5jWg3/ORId4YzBBit3t1q+Hb/FdaucLeH3O/T8VynL71bNo/lrf+HXyrF5\n"
                                "DTuT2pUuMCWFqM3Vjdlh9f5NHts/mgKtAgMBAAGjSDBGMA4GA1UdDwEB/wQEAwIH\n"
                                "gDATBgNVHSUEDDAKBggrBgEFBQcDAjAfBgNVHSMEGDAWgBQgHgcJSP9n1F56KYq5\n"
                                "Vfw7DNNsPzANBgkqhkiG9w0BAQwFAAOCAgEAUh+ZhNPGNgfuzmFCecanZe3jrsA3\n"
                                "soO7QOm8LdCJWRpYRvEvURZj7TfaJKICha7YraYha2z9yO5hs0PZ2yrZ0dAXkK4L\n"
                                "1R9qroXLV3Jn+4ys1hj4eN4ppHJS4+wVejXVvpWm6jmXfbF0L1xOcXSkzFDW2htj\n"
                                "mt0gFGi8HXPECGLYiNp+hsLFOnO6n+o2LDskmcTvt35sgA60TDCLZbHnms4meGFQ\n"
                                "bOKeI6oQOB3WIrsZUDq+q16Je27VnS/THXJbWmC9Fg/DZSWdPqVrF42jL7p2UQXU\n"
                                "PTefwn9LRmEuOcJ5DECj4DE4E5Ui3E52ue7RojpsfbKuwzMw+4NgHKz3e71FHPTF\n"
                                "7fdjGDDsrK8yriV/l6wEOrzG2ORXBil+fzfvqgO6AA10L0UU+VQwtR6WA20b7mXr\n"
                                "QJH5w9Q/azr6FPf1QqzyI9hFdtphkLJA7A4pIs2phRzU6DFq92NZZMgDLX/ZIV95\n"
                                "Gvihzr1keEAQx5RBLcq6IAiGKqv0CaE7XSbPOfdXzvbK2d2ZW015U+fUtUYhumyx\n"
                                "ssiau1OC9mbwEfIQ5epnkFBHxaw7Ex8kNMuLiR2G32SHFvrtkjM0NAXI9EgxlHFl\n"
                                "Ym9vKezQyhOrc7vxZBEph2zjmXpmCyneSnoKigRxjnd2SZXMh10Srj59dMPmZJma\n"
                                "R6xlbbaWzvi8Xck=\n"
                                "-----END CERTIFICATE-----\n";

    QByteArray certificateData = certificateString.toUtf8();

    QSslCertificate certificate(certificateData, QSsl::Pem);
    sslConfiguration.addCaCertificate(certificate);
    // m_webSocket.setSslConfiguration(sslConfiguration);

    m_webSocket.open(QString::fromUtf8("wss://stream.bybit.com/v5/private"));
}

void ByBitBalancer::close()
{
    m_webSocket.close();
}

void ByBitBalancer::onConnected()
{
    qDebug() << "[ByBitBalancer::onConnected] ";
}

void ByBitBalancer::onDisconnected()
{
    qDebug() << "[ByBitBalancer::onDisconnected] ";
}

void ByBitBalancer::onSslErrors(const QList<QSslError> &errors)
{
    qDebug() << "[ByBitBalancer::onSslErrors] " << errors;
}

void ByBitBalancer::onError(QAbstractSocket::SocketError error)
{
    Q_UNUSED(error);
    qDebug() << "[ByBitBalancer::onError] Ошибка WebSocket:" << m_webSocket.errorString();
    onDisconnected();
}
