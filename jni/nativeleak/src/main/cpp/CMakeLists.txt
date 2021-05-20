# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

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
        test-lib
        ${log-lib})