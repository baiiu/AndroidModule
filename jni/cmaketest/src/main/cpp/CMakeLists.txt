# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# ./gradlew :jni:cmaketest:externalNativeBuildDebug

message(CMAKE_CURRENT_LIST_DIR: ${CMAKE_CURRENT_LIST_DIR})
message(CMAKE_CURRENT_LIST_FILE: ${CMAKE_CURRENT_LIST_FILE})
message(CMAKE_SOURCE_DIR: ${CMAKE_SOURCE_DIR})
message(CMAKE_ANDROID_ARCH: ${CMAKE_ANDROID_ARCH})
message(CMAKE_ANDROID_ARCH_ABI: ${CMAKE_ANDROID_ARCH_ABI})

set(var hello)
message(${var})

set(var 111)
message(${var})

option(SHOW_LOG "whether to show log" ON)
#option(SHOW_LOG "whether to show log" OFF)
message(SHOW_LOG: ${SHOW_LOG})
if (${SHOW_LOG})
    message(SHOW_LOG ON: ${SHOW_LOG})
ELSE ()
    message(SHOW_LOG OFF: ${SHOW_LOG})
ENDIF ()

message(ENABLE_LOG:${ENABLE_LOG})
if (${ENABLE_LOG})
    message(log on)
    add_definitions(-D ENABLE_LOG)
else ()
    message(log off)
ENDIF ()


message(TRANSPORT_LAYER: ${TRANSPORT_LAYER})

# base
message(transport: 000000000000)
set(src_files native-lib.cpp)

if (${TRANSPORT_LAYER} EQUAL 1)
    message(transport: 111111111111)
    add_definitions(-DENABLE_FIBO)
    set(src_files ${src_files} fibo.cpp)
    message(src_files: ${src_files})
ENDIF ()

add_library(native-lib SHARED ${src_files})
find_library(log-lib log)
target_link_libraries(native-lib ${log-lib})


if (${TRANSPORT_LAYER} EQUAL 2)
    message(transport: 22222222222)
ENDIF ()

if (${TRANSPORT_LAYER} EQUAL 3)
    message(transport: 333333333333)
ENDIF ()


# test boolean
if (TRUE)
    message(testvar: ${var})
ENDIF ()

if (1)
    message(test1)
ENDIF ()

if (0)
    message(test0)
ENDIF ()

if (NO)
    message(testNo)
ENDIF ()

if (YES)
    message(testYES)
ENDIF ()

