# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

message(boolean test)

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


# list操作
# set(var a b c d e) creates a list with a;b;c;d;e, and set(var “a b c d e”) creates a string or a list with one item in it.
set(helloList a b c d e)
list(REMOVE_ITEM helloList a)
list(REMOVE_AT helloList 0)
list(INSERT helloList 0 /Users/baiiu/framework/android-ndk-r14b/sources/cxx-stl/llvm-libc++/libs/armeabi/libc++_shared.so)
list(APPEND helloList last)
message(helloList: ${helloList})
foreach (item ${helloList})
    message(item: ${item})
endforeach ()

foreach (LIB ${CMAKE_CXX_IMPLICIT_LINK_LIBRARIES})
    message(LIB: ${LIB})
endforeach ()