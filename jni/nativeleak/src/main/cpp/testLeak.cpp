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

extern "C"
JNIEXPORT void JNICALL
Java_com_baiiu_nativeleak_MainActivity_testLeak(JNIEnv *env, jobject thiz) {
    char *p = (char *) malloc(sizeof(char) * 1024 * 1024);
    char *s = "hello_world";
    if (p) {
        strcpy(p, s);
    }
    __android_log_print(ANDROID_LOG_DEBUG, "mLogU", "%p has leaked 1M", p);
}
