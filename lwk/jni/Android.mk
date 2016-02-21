LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

OpenCV_INSTALL_MODULES := on
OPENCV_CAMERA_MODULES := off
OPENCV_LIB_TYPE := STATIC

include ${OPENCV_ANDROID}/sdk/native/jni/OpenCV.mk

LOCAL_C_INCLUDES := ${OPENCV_ANDROID}/sdk/native/jni/include \
				$(LOCAL_PATH)/jhfilter

FILE_LIST := $(wildcard $(LOCAL_PATH)/jhfilter/*.cpp)

LOCAL_MODULE := imagefilter
LOCAL_SRC_FILES := imagefilter.cpp \
				$(FILE_LIST:$(LOCAL_PATH)/%=%)
LOCAL_LDLIBS :=  -llog -ldl -lz

include $(BUILD_SHARED_LIBRARY)