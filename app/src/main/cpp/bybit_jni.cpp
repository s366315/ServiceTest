#include <jni.h>
#include "ByBitBalancer.h"

// Простая структура для хранения указателя
// static QPointer<ByBitBalancer> s_balancer = nullptr;

// ByBitBalancer * client = new ByBitBalancer();

extern "C" {

JNIEXPORT void JNICALL
Java_com_servicetest_NativeWrapper_open(JNIEnv *env, jobject thiz)
{
    ByBitBalancer * client = new ByBitBalancer();
    client->open();

    // ByBitBalancer *b = reinterpret_cast<ByBitBalancer *>(ptr);
    // if (!b) return;

    // Вызов в потоке объекта
    // QMetaObject::invokeMethod(b, [b]() { b->open(); }, Qt::QueuedConnection);
}

JNIEXPORT void JNICALL
Java_com_servicetest_NativeWrapper_close(JNIEnv *env, jobject thiz)
{
   /* ByBitBalancer *b = reinterpret_cast<ByBitBalancer *>(ptr);
    if (!b) return;
    QMetaObject::invokeMethod(b, "close", Qt::QueuedConnection);*/
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    return JNI_VERSION_1_6;
}

} // extern "C"
