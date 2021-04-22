# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# https://cmake.org/cmake/help/latest/command/function.html

function(functionName args)
    message(function-->args: ${args})
endfunction()

functionName(helloFunction)


#macro()
#endmacro()


set(mList 1 2 3 first)
message(mList: ${mList})


function(whenOne arg)
    #    直接操作arg无效，形参
    #    list(APPEND arg 111)
    #    set(arg ${arg} 111)
    message(arg: ${arg})
    message(ARGV: ${ARGV})
    set(ARGV ${ARGV} one one one PARENT_SCOPE)
    message(whenOne-->${ARGV})

    list(APPEND ARGV one one one PARENT_SCOPE)
    message(whenOne-->${ARGV})
endfunction()

function(whenTwo arg)
    #    list(APPEND arg 222)
#    message(whenTwo-->${arg})
endfunction()


if (${TRANSPORT_LAYER} EQUAL 1)
    message(transport: 111111111111)
    whenOne(mList)
ENDIF ()

if (${TRANSPORT_LAYER} EQUAL 2)
    message(transport: 22222222222)
    whenTwo(mList)
ENDIF ()

if (${TRANSPORT_LAYER} EQUAL 3)
    message(transport: 333333333333)
    whenOne(${mList})
    whenTwo(${mList})
ENDIF ()

list(APPEND mList last)
set(mList ${mList} 1 2 3 4 5 6)
message(mList: ${mList})


