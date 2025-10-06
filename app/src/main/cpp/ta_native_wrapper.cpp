#include <jni.h>
#include "ta_libc.h"

extern "C" JNIEXPORT jdouble JNICALL
Java_com_servicetest_TANativeWrapper_sma(
    JNIEnv* env,
    jobject /* this */,
    jdoubleArray input,
    jint period
) {
    jsize len = env->GetArrayLength(input);
    jdouble* in = env->GetDoubleArrayElements(input, nullptr);

    double out[len];
    int outBeg, outNb;

    // Инициализация TA-Lib
    TA_Initialize();

    TA_RetCode ret = TA_MA(0, len - 1, in, period, TA_MAType_SMA,
                           &outBeg, &outNb, out);

    TA_Shutdown();

    env->ReleaseDoubleArrayElements(input, in, 0);

    if (ret != TA_SUCCESS || outNb <= 0) {
        return -1; // ошибка
    }

    return out[outNb - 1]; // последнее значение SMA
}
