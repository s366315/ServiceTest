cmake_minimum_required(VERSION 3.10)

project(bybit_balancer LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)

set(CMAKE_AUTOMOC ON)   # обязательно!
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)
set(CMAKE_FIND_ROOT_PATH /Users/eugene/Qt/6.9.2/android_arm64_v8a)
set(CMAKE_PREFIX_PATH /Users/eugene/Qt/6.9.2/android_arm64_v8a)
set(ANDROID_SDK_ROOT /Users/eugene/Library/Android/sdk)

find_package(Qt6 REQUIRED COMPONENTS Core Network WebSockets)

include(FetchContent)
FetchContent_Declare(
        android_openssl
        DOWNLOAD_EXTRACT_TIMESTAMP true
        URL https://github.com/KDAB/android_openssl/archive/refs/heads/master.zip
)
FetchContent_MakeAvailable(android_openssl)
include(${android_openssl_SOURCE_DIR}/android_openssl.cmake)
qt_add_executable(your_target_name ..)
qt_add_executable(your_second_target_name ..)
add_android_openssl_libraries(your_target_name your_second_target_name)

# JNI библиотека
add_library(bybit_balancer SHARED
        ByBitBalancer.cpp
        ByBitBalancer.h
        bybit_jni.cpp
)

target_include_directories(bybit_balancer PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})

target_link_libraries(bybit_balancer PRIVATE
        Qt6::Core
        Qt6::Network
        Qt::WebSockets
        android
        log
)
