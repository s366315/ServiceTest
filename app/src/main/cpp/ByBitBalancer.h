#ifndef BYBITBALANCER_H
#define BYBITBALANCER_H

#include <QObject>
#include <QWebSocket>
#include <QtCore/qglobal.h>

class ByBitBalancer : public QObject
{
    Q_OBJECT
public:
    explicit ByBitBalancer(QObject *parent = nullptr);

    Q_INVOKABLE void open();
    Q_INVOKABLE void close();

private slots:
    void onConnected();
    void onDisconnected();
    void onSslErrors(const QList<QSslError> &errors);
    void onError(QAbstractSocket::SocketError error);

private:
    QWebSocket m_webSocket;
};

#endif // BYBITBALANCER_H
