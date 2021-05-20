# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#aux_source_directory(xhook xhook_files)
file(GLOB xhook_files xhook/*.c)

add_library(
        leak-monitor-lib

        SHARED

        native-lib.cpp
        ${xhook_files}
)

# 添加动态库
add_library(
        test-lib

        SHARED

        testLeak.cpp
)

# 从某路径下找到动态库并命名
find_library(
        log-lib
        log)

# 关联动态库
target_link_libraries(
        leak-monitor-lib
        ${log-lib}
        -lz
)

target_link_libraries(
        test-lib
        ${log-lib}
        -lz
)