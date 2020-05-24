# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

set(var hello)
message(${var})

set(var 111)
message(${var})

message(${CMAKE_CURRENT_LIST_DIR})
message(${CMAKE_CURRENT_LIST_FILE})

if (TRUE)
    message(${var})
ENDIF ()

# 添加动态库
add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)
add_library(
        people-lib
        SHARED
        people/people.cpp
)
add_library(
        dynamic-lib
        SHARED
        dynamic/dynamic-lib.cpp
)
add_library(
        typeConvert-lib
        SHARED
        typeConvert/TypeConvert.cpp
)
add_library(
        string-lib
        SHARED
        string/StringTest.cpp
)
add_library(
        reference-lib
        SHARED
        reference/ReferenceTest.cpp
)
add_library(
        thread-lib
        SHARED
        thread/threadTest.cpp
)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 从某路径下找到动态库并命名
find_library(
        # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that you want CMake to locate.
        log)


# 添加到<>寻找路径中
include_directories(people/)
include_directories(base/)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

# 关联动态库
target_link_libraries( # Specifies the target library.
        native-lib
        people-lib

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

target_link_libraries(
        dynamic-lib
        ${log-lib}
)
target_link_libraries(
        typeConvert-lib
        ${log-lib}
)
target_link_libraries(
        string-lib
        ${log-lib}
)
target_link_libraries(
        reference-lib
        ${log-lib}
)
target_link_libraries(
        thread-lib
        ${log-lib}
)