//
// Created by baiiu on 2021/5/20.
//

#include <android/log.h>
#include <malloc.h>
#include "jni.h"
#include "string.h"

/*
 *
 *
这段代码：
char *p = (char *) malloc(sizeof(char) * 1024 * 1024);
__android_log_print(ANDROID_LOG_DEBUG, "mLogU", "%p has leaked 1M", p);
每次分配1M，当虚拟内存分配完后，返回指针为null；
点Home键后，直接崩溃了；

adb shell cat proc/27581/statm
363265 25825 17479 5 0 38044 0
624514 26183 17810 5 0 299292 0
886658 26352 17953 5 0 561436 0
1044866 26447 18021 5 0 719644 0
1044866 26457 18037 5 0 719644 0
1044866 26460 18037 5 0 719644 0
通过proc/[pid]/statm查看，当虚拟内存全部分配后，第一列vmSize已经不再变化，因为没有引用该值，索引


05-20 17:16:54.259 27435 27435 E AndroidRuntime: FATAL EXCEPTION: main
05-20 17:16:54.259 27435 27435 E AndroidRuntime: Process: com.baiiu.nativeleak, PID: 27435
05-20 17:16:54.259 27435 27435 E AndroidRuntime: java.lang.OutOfMemoryError: pthread_create (1040KB stack) failed: Try again
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at java.lang.Thread.nativeCreate(Native Method)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at java.lang.Thread.start(Thread.java:733)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.QueuedWork.getHandler(QueuedWork.java:104)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.QueuedWork.waitToFinish(QueuedWork.java:153)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.ActivityThread.handleStopActivity(ActivityThread.java:4207)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.servertransaction.StopActivityItem.execute(StopActivityItem.java:41)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.servertransaction.TransactionExecutor.executeLifecycleState(TransactionExecutor.java:145)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:70)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1808)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.os.Handler.dispatchMessage(Handler.java:106)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.os.Looper.loop(Looper.java:193)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at android.app.ActivityThread.main(ActivityThread.java:6669)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at java.lang.reflect.Method.invoke(Native Method)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:493)
05-20 17:16:54.259 27435 27435 E AndroidRuntime: 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:858)



char *p = (char *) malloc(sizeof(char) * 1024 * 1024);
char *s = "hello_world";
strcpy(p, s);
__android_log_print(ANDROID_LOG_DEBUG, "mLogU", "%p has leaked 1M", p);
每次分配1M，当虚拟内存分配完后，返回指针为null；为null时调用strcpy因空指针崩溃
当修复空指针问题后，按Home键崩溃时候日志和第一种一样

generic_x86_arm:/ $ cat proc/27850/statm
363265 25782 17421 5 0 38044 0
624514 27164 17735 5 0 299292 0
886786 28203 17741 5 0 561564 0


05-20 17:46:57.373 27945 27945 F DEBUG   : Build fingerprint: 'google/sdk_gphone_x86_arm/generic_x86_arm:9/PSR1.180720.117/5875966:user/release-keys'
05-20 17:46:57.373 27945 27945 F DEBUG   : Revision: '0'
05-20 17:46:57.373 27945 27945 F DEBUG   : ABI: 'x86'
05-20 17:46:57.373 27945 27945 F DEBUG   : pid: 27850, tid: 27850, name: aiiu.nativeleak  >>> com.baiiu.nativeleak <<<
05-20 17:46:57.373 27945 27945 F DEBUG   : signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x0
05-20 17:46:57.373 27945 27945 F DEBUG   : Cause: null pointer dereference
05-20 17:46:57.373 27945 27945 F DEBUG   :     eax 00000000  ebx d9211fdc  ecx f349f2a0  edx 00646c72
05-20 17:46:57.373 27945 27945 F DEBUG   :     edi 00000000  esi d9210652
05-20 17:46:57.373 27945 27945 F DEBUG   :     ebp ff7fc328  esp ff7fc2c4  eip f349f2a7
05-20 17:46:57.479 27945 27945 F DEBUG   :
05-20 17:46:57.479 27945 27945 F DEBUG   : backtrace:
05-20 17:46:57.479 27945 27945 F DEBUG   :     #00 pc 0001e2a7  /system/lib/libc.so (strcpy+1591)
05-20 17:46:57.479 27945 27945 F DEBUG   :     #01 pc 00000619  /data/app/com.baiiu.nativeleak-wL45YswCp5R9mJtUY58XoQ==/lib/x86/libnative-lib.so (Java_com_baiiu_nativeleak_MainActivity_testLeak+137)

由此可见虚拟内存使用完了造成的影响很大，无法mallcoc，正常业务无法运行；
首先应当尽量避免内存泄露，然后看看能否使用64位so，增大虚拟内存寻址空间；
 */

void leakIt() {
    char *p = (char *) malloc(sizeof(char) * 1024 * 1024);
    char *s = "hello_world";
    if (p) {
        strcpy(p, s);
    }
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "%p has leaked 1M", p);
//    free(p);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_nativeleak_MainActivity_testLeak(JNIEnv *env, jobject thiz) {
    leakIt();
}


/*
05-21 10:50:52.185 16348 16348 I wrap.sh : =================================================================
05-21 10:50:52.185 16348 16348 I wrap.sh : ==16363==ERROR: AddressSanitizer: heap-use-after-free on address 0xc858d495 at pc 0xbb5d1a40 bp 0xfff51df8 sp 0xfff51df0
05-21 10:50:52.185 16348 16348 I wrap.sh : READ of size 1 at 0xc858d495 thread T0 (aiiu.nativeleak)
05-21 10:50:52.194  1642  5118 W audio_hw_generic: Not supplying enough data to HAL, expected position 948690996 , only wrote 948690720
05-21 10:50:52.202 16348 16348 I wrap.sh :     #0 0xbb5d1a3f  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0xa3f)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #1 0xbb5d199a  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0x99a)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #2 0xc6631a17  (/system/lib/libart.so+0x5f6a17)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #3 0xc662ba02  (/system/lib/libart.so+0x5f0a02)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #4 0xc60ddf4e  (/system/lib/libart.so+0xa2f4e)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #5 0xc62d6b22  (/system/lib/libart.so+0x29bb22)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #6 0xc62cecc8  (/system/lib/libart.so+0x293cc8)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #7 0xc65f8926  (/system/lib/libart.so+0x5bd926)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #8 0xc661dca1  (/system/lib/libart.so+0x5e2ca1)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #9 0xc62a1096  (/system/lib/libart.so+0x266096)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #10 0xc62a750e  (/system/lib/libart.so+0x26c50e)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #11 0xc65e43fd  (/system/lib/libart.so+0x5a93fd)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #12 0xc6631aed  (/system/lib/libart.so+0x5f6aed)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #13 0xc662ba02  (/system/lib/libart.so+0x5f0a02)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #14 0xc60ddf4e  (/system/lib/libart.so+0xa2f4e)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #15 0xc650e209  (/system/lib/libart.so+0x4d3209)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #16 0xc650feee  (/system/lib/libart.so+0x4d4eee)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #17 0xc6484643  (/system/lib/libart.so+0x449643)
05-21 10:50:52.202 16348 16348 I wrap.sh :     #18 0x706db778  (/system/framework/x86/boot.oat+0x11d778)
05-21 10:50:52.202 16348 16348 I wrap.sh :
05-21 10:50:52.203 16348 16348 I wrap.sh : 0xc858d495 is located 5 bytes inside of 40-byte region [0xc858d490,0xc858d4b8)
05-21 10:50:52.203 16348 16348 I wrap.sh : freed by thread T0 (aiiu.nativeleak) here:
05-21 10:50:52.203 16348 16348 I wrap.sh :     #0 0xee60f557  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libclang_rt.asan-i686-android.so+0xb6557)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #1 0xbb5d19f7  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0x9f7)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #2 0xbb5d199a  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0x99a)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #3 0xc6631a17  (/system/lib/libart.so+0x5f6a17)
05-21 10:50:52.203 16348 16348 I wrap.sh :
05-21 10:50:52.203 16348 16348 I wrap.sh : previously allocated by thread T0 (aiiu.nativeleak) here:
05-21 10:50:52.203 16348 16348 I wrap.sh :     #0 0xee60f7d5  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libclang_rt.asan-i686-android.so+0xb67d5)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #1 0xbb5d19e3  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0x9e3)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #2 0xbb5d199a  (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0x99a)
05-21 10:50:52.203 16348 16348 I wrap.sh :     #3 0xc6631a17  (/system/lib/libart.so+0x5f6a17)
05-21 10:50:52.203 16348 16348 I wrap.sh :
05-21 10:50:52.203 16348 16348 I wrap.sh : SUMMARY: AddressSanitizer: heap-use-after-free (/data/app/com.baiiu.nativeleak-7nEnc7bKae8AkZ7m5wBJPQ==/lib/x86/libtest-lib.so+0xa3f)
05-21 10:50:52.203 16363 16363 F libc    : Fatal signal 6 (SIGABRT), code -6 (SI_TKILL) in tid 16363 (aiiu.nativeleak), pid 16363 (aiiu.nativeleak)
05-21 10:50:52.204 16348 16348 I wrap.sh : Shadow bytes around the buggy address:
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721a40: fa fa fd fd fd fd fd fa fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721a50: fa fa fd fd fd fd fd fd fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721a60: fa fa fd fd fd fd fd fd fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721a70: fa fa fd fd fd fd fd fd fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721a80: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:50:52.204 16348 16348 I wrap.sh : =>0xe3721a90: fa fa[fd]fd fd fd fd fa fa fa fa fa fa fa fa fa
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721aa0: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721ab0: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721ac0: fa fa fa fa fa fa fa fa fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721ad0: fa fa fd fd fd fd fd fd fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   0xe3721ae0: fa fa 00 00 00 00 00 04 fa fa fd fd fd fd fd fd
05-21 10:50:52.204 16348 16348 I wrap.sh : Shadow byte legend (one shadow byte represents 8 application bytes):
05-21 10:50:52.204 16348 16348 I wrap.sh :   Addressable:           00
05-21 10:50:52.204 16348 16348 I wrap.sh :   Partially addressable: 01 02 03 04 05 06 07
05-21 10:50:52.204 16348 16348 I wrap.sh :   Heap left redzone:       fa
05-21 10:50:52.204 16348 16348 I wrap.sh :   Freed heap region:       fd
05-21 10:50:52.204 16348 16348 I wrap.sh :   Stack left redzone:      f1
05-21 10:50:52.204 16348 16348 I wrap.sh :   Stack mid redzone:       f2
05-21 10:50:52.204 16348 16348 I wrap.sh :   Stack right redzone:     f3
05-21 10:50:52.204 16348 16348 I wrap.sh :   Stack after return:      f5
05-21 10:50:52.204 16348 16348 I wrap.sh :   Stack use after scope:   f8
05-21 10:50:52.204 16348 16348 I wrap.sh :   Global redzone:          f9
05-21 10:50:52.204 16348 16348 I wrap.sh :   Global init order:       f6
05-21 10:50:52.204 16348 16348 I wrap.sh :   Poisoned by user:        f7
05-21 10:50:52.204 16348 16348 I wrap.sh :   Container overflow:      fc
05-21 10:50:52.204 16348 16348 I wrap.sh :   Array cookie:            ac
05-21 10:50:52.204 16348 16348 I wrap.sh :   Intra object redzone:    bb
05-21 10:50:52.204 16348 16348 I wrap.sh :   ASan internal:           fe
05-21 10:50:52.204 16348 16348 I wrap.sh :   Left alloca redzone:     ca
05-21 10:50:52.204 16348 16348 I wrap.sh :   Right alloca redzone:    cb
05-21 10:50:52.204 16348 16348 I wrap.sh :   Shadow gap:              cc
05-21 10:50:52.204 16348 16348 I wrap.sh : ==16363==ABORTING
 */
static char heapUseAfterFree() {
    char *x = (char *) malloc(10 * sizeof(char *));
    free(x);
    return x[5];
}

/*
05-21 10:48:25.693 16157 16157 I wrap.sh : =================================================================
05-21 10:48:25.693 16157 16157 I wrap.sh : ==16168==ERROR: AddressSanitizer: heap-buffer-overflow on address 0xa4e06100 at pc 0xbbdf0a89 bp 0xffabdf08 sp 0xffabdf00
05-21 10:48:25.693 16157 16157 I wrap.sh : WRITE of size 4 at 0xa4e06100 thread T0 (aiiu.nativeleak)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #0 0xbbdf0a88  (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libtest-lib.so+0xa88)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #1 0xbbdf099a  (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libtest-lib.so+0x99a)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #2 0xc6e3da17  (/system/lib/libart.so+0x5f6a17)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #3 0xc6e37a02  (/system/lib/libart.so+0x5f0a02)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #4 0xc68e9f4e  (/system/lib/libart.so+0xa2f4e)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #5 0xc6ae2b22  (/system/lib/libart.so+0x29bb22)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #6 0xc6adacc8  (/system/lib/libart.so+0x293cc8)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #7 0xc6e04926  (/system/lib/libart.so+0x5bd926)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #8 0xc6e29ca1  (/system/lib/libart.so+0x5e2ca1)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #9 0xc6aad096  (/system/lib/libart.so+0x266096)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #10 0xc6ab350e  (/system/lib/libart.so+0x26c50e)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #11 0xc6df03fd  (/system/lib/libart.so+0x5a93fd)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #12 0xc6e3daed  (/system/lib/libart.so+0x5f6aed)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #13 0xc6e37a02  (/system/lib/libart.so+0x5f0a02)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #14 0xc68e9f4e  (/system/lib/libart.so+0xa2f4e)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #15 0xc6d1a209  (/system/lib/libart.so+0x4d3209)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #16 0xc6d1beee  (/system/lib/libart.so+0x4d4eee)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #17 0xc6c90643  (/system/lib/libart.so+0x449643)
05-21 10:48:25.710 16157 16157 I wrap.sh :     #18 0x706db778  (/system/framework/x86/boot.oat+0x11d778)
05-21 10:48:25.710 16157 16157 I wrap.sh :
05-21 10:48:25.710 16157 16157 I wrap.sh : 0xa4e06100 is located 0 bytes to the right of 4096-byte region [0xa4e05100,0xa4e06100)
05-21 10:48:25.710 16157 16157 I wrap.sh : allocated by thread T0 (aiiu.nativeleak) here:
05-21 10:48:25.711 16168 16168 F libc    : Fatal signal 6 (SIGABRT), code -6 (SI_TKILL) in tid 16168 (aiiu.nativeleak), pid 16168 (aiiu.nativeleak)
05-21 10:48:25.711 16157 16157 I wrap.sh :     #0 0xee787229  (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libclang_rt.asan-i686-android.so+0xc2229)
05-21 10:48:25.711 16157 16157 I wrap.sh :     #1 0xbbdf09e4  (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libtest-lib.so+0x9e4)
05-21 10:48:25.711 16157 16157 I wrap.sh :     #2 0xbbdf099a  (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libtest-lib.so+0x99a)
05-21 10:48:25.711 16157 16157 I wrap.sh :     #3 0xc6e3da17  (/system/lib/libart.so+0x5f6a17)
05-21 10:48:25.711 16157 16157 I wrap.sh :
05-21 10:48:25.711 16157 16157 I wrap.sh : SUMMARY: AddressSanitizer: heap-buffer-overflow (/data/app/com.baiiu.nativeleak-_3kn4qdpJkBU1MuE2YlYUQ==/lib/x86/libtest-lib.so+0xa88)
05-21 10:48:25.711 16157 16157 I wrap.sh : Shadow bytes around the buggy address:
05-21 10:48:25.711 16157 16157 I wrap.sh :   0xdf7d8bd0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
05-21 10:48:25.711 16157 16157 I wrap.sh :   0xdf7d8be0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
05-21 10:48:25.711 16157 16157 I wrap.sh :   0xdf7d8bf0: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
05-21 10:48:25.711 16157 16157 I wrap.sh :   0xdf7d8c00: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c10: 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
05-21 10:48:25.712 16157 16157 I wrap.sh : =>0xdf7d8c20:[fa]fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c30: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c40: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c50: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c60: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   0xdf7d8c70: fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa fa
05-21 10:48:25.712 16157 16157 I wrap.sh : Shadow byte legend (one shadow byte represents 8 application bytes):
05-21 10:48:25.712 16157 16157 I wrap.sh :   Addressable:           00
05-21 10:48:25.712 16157 16157 I wrap.sh :   Partially addressable: 01 02 03 04 05 06 07
05-21 10:48:25.712 16157 16157 I wrap.sh :   Heap left redzone:       fa
05-21 10:48:25.712 16157 16157 I wrap.sh :   Freed heap region:       fd
05-21 10:48:25.712 16157 16157 I wrap.sh :   Stack left redzone:      f1
05-21 10:48:25.712 16157 16157 I wrap.sh :   Stack mid redzone:       f2
05-21 10:48:25.712 16157 16157 I wrap.sh :   Stack right redzone:     f3
05-21 10:48:25.712 16157 16157 I wrap.sh :   Stack after return:      f5
05-21 10:48:25.712 16157 16157 I wrap.sh :   Stack use after scope:   f8
05-21 10:48:25.712 16157 16157 I wrap.sh :   Global redzone:          f9
05-21 10:48:25.712 16157 16157 I wrap.sh :   Global init order:       f6
05-21 10:48:25.712 16157 16157 I wrap.sh :   Poisoned by user:        f7
05-21 10:48:25.712 16157 16157 I wrap.sh :   Container overflow:      fc
05-21 10:48:25.712 16157 16157 I wrap.sh :   Array cookie:            ac
05-21 10:48:25.712 16157 16157 I wrap.sh :   Intra object redzone:    bb
05-21 10:48:25.712 16157 16157 I wrap.sh :   ASan internal:           fe
05-21 10:48:25.712 16157 16157 I wrap.sh :   Left alloca redzone:     ca
05-21 10:48:25.712 16157 16157 I wrap.sh :   Right alloca redzone:    cb
05-21 10:48:25.712 16157 16157 I wrap.sh :   Shadow gap:              cc
05-21 10:48:25.712 16157 16157 I wrap.sh : ==16168==ABORTING
 */
static void heapBufferOverflow() {
    int *arr = new int[1024];
    arr[0] = 11;
    arr[1024] = 12;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_nativeleak_MainActivity_testASan(JNIEnv *env, jobject thiz) {
//    heapBufferOverflow();
    heapUseAfterFree();
}
