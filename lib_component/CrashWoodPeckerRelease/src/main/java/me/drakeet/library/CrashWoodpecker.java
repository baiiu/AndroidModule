/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 drakeet (drakeet.me@gmail.com)
 * http://drakeet.me
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.drakeet.library;

import android.app.Application;
import android.content.Context;

/**
 * Created by drakeet(http://drakeet.me)
 * Date: 9/6/15 13:40
 */
@SuppressWarnings("unused")
public class CrashWoodpecker implements Thread.UncaughtExceptionHandler {

    public static CrashWoodpecker flyTo(Application application, boolean forceHandleByOrigin) {
        return new CrashWoodpecker(application, forceHandleByOrigin);
    }


    public static CrashWoodpecker flyTo(Application application) {
        return new CrashWoodpecker(application, false);
    }

    //public static CrashWoodpecker init(Application application, boolean forceHandleByOrigin) {
    //    return new CrashWoodpecker(application, forceHandleByOrigin);
    //}
    //
    //
    //public static CrashWoodpecker init(Application application) {
    //    return new CrashWoodpecker(application, false);
    //}


    public void deleteLogs() {
    }


    public void deleteLogs(final long timeout) {
    }


    private CrashWoodpecker(Context context, boolean forceHandleByOrigin) { }


    @Override public void uncaughtException(Thread thread, Throwable ex) {

    }


    public CrashWoodpecker withKeys(final String... keys) {
        return this;
    }


    public CrashWoodpecker setInterceptor(UncaughtExceptionInterceptor interceptor) {
        return this;
    }


    public interface UncaughtExceptionInterceptor {
        boolean onInterceptExceptionBefore(Thread t, Throwable ex);
        boolean onInterceptExceptionAfter(Thread t, Throwable ex);
    }
}
