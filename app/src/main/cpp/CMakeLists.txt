cmake_minimum_required(VERSION 3.10)

project(servicetest-native)

# Подключаем ta-lib
add_subdirectory(${CMAKE_SOURCE_DIR}/../../../../third_party/ta-lib ta-lib-build)

# JNI библиотека
add_library(ta-native SHARED ta_native_wrapper.cpp)

#find_package(openssl REQUIRED CONFIG)

target_link_libraries(
        ta-native
        ta-lib
        android
        log
)
