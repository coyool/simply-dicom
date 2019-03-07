/*
 * Copyright (C) 2013 - 2015. Christopher Boyd
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.opencv.core;

import java.util.Arrays;
import java.util.List;

public class MatOfByte extends Mat {
    // 8UC(x)
    private static final int _depth = CvType.CV_8U;
    private static final int _channels = 1;

    public MatOfByte() {
        super();
    }

    protected MatOfByte(long addr) {
        super(addr);
        if( !empty() && checkVector(_channels, _depth) < 0 )
            throw new IllegalArgumentException("Incompatible Mat");
        //FIXME: do we need release() here?
    }

    public static MatOfByte fromNativeAddr(long addr) {
        return new MatOfByte(addr);
    }

    public MatOfByte(Mat m) {
        super(m, Range.all());
        if( !empty() && checkVector(_channels, _depth) < 0 )
            throw new IllegalArgumentException("Incompatible Mat");
        //FIXME: do we need release() here?
    }

    public MatOfByte(byte...a) {
        super();
        fromArray(a);
    }

    public void alloc(int elemNumber) {
        if(elemNumber>0)
            super.create(elemNumber, 1, CvType.makeType(_depth, _channels));
    }

    public void fromArray(byte...a) {
        if(a==null || a.length==0)
            return;
        int num = a.length / _channels;
        alloc(num);
        put(0, 0, a); //TODO: check ret val!
    }

    public byte[] toArray() {
        int num = checkVector(_channels, _depth);
        if(num < 0)
            throw new RuntimeException("Native Mat has unexpected type or size: " + toString());
        byte[] a = new byte[num * _channels];
        if(num == 0)
            return a;
        get(0, 0, a); //TODO: check ret val!
        return a;
    }

    public void fromList(List<Byte> lb) {
        if(lb==null || lb.size()==0)
            return;
        Byte ab[] = lb.toArray(new Byte[0]);
        byte a[] = new byte[ab.length];
        for(int i=0; i<ab.length; i++)
            a[i] = ab[i];
        fromArray(a);
    }

    public List<Byte> toList() {
        byte[] a = toArray();
        Byte ab[] = new Byte[a.length];
        for(int i=0; i<a.length; i++)
            ab[i] = a[i];
        return Arrays.asList(ab);
    }
}
