/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_hanaone_ip_ImageFilter */
#include "CurvesFilter.h"

#ifndef _Included_com_hanaone_ip_ImageFilter
#define _Included_com_hanaone_ip_ImageFilter
#ifdef __cplusplus
extern "C" {
#endif
#undef com_hanaone_ip_ImageFilter_FILTER_HSB_ADJUST
#define com_hanaone_ip_ImageFilter_FILTER_HSB_ADJUST 0L

#define PI 3.14159265359
/*
 * Class:     com_hanaone_ip_ImageFilter
 * Method:    hsbAdFilter
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_hsbAdFilter
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     com_hanaone_ip_ImageFilter_WhiteBalance
 * Method:    WhiteBalance
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_WhiteBalance
  (JNIEnv *, jobject, jstring, jstring, jfloat, jfloat);

/*
 * Class:     com_hanaone_ip_ImageFilter_nativeCurveFilter
 * Method:    WhiteBalance
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_nativeCurveFilter
  (JNIEnv *, jobject, jstring, jstring);
#ifdef __cplusplus
}
#endif
#endif
