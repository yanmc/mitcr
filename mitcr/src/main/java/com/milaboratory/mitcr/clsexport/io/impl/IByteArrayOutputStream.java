/*
 * MiTCR <http://milaboratory.com>
 *
 * Copyright (c) 2010-2013:
 *     Bolotin Dmitriy     <bolotin.dmitriy@gmail.com>
 *     Chudakov Dmitriy    <chudakovdm@mail.ru>
 *
 * MiTCR is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.milaboratory.mitcr.clsexport.io.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Non-synchronized copy of java.io.ByteArrayOutputStream + writeTo RandomAccessFile
 */
public class IByteArrayOutputStream extends OutputStream {
    private byte buf[];
    private int count;

    public IByteArrayOutputStream() {
        this(32);
    }

    public IByteArrayOutputStream(int size) {
        if (size < 0)
            throw new IllegalArgumentException("Negative initial size: "
                    + size);
        buf = new byte[size];
    }

    @Override
    public void write(int b) {
        int newcount = count + 1;
        if (newcount > buf.length)
            buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
        buf[count] = (byte) b;
        count = newcount;
    }

    @Override
    public void write(byte b[], int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0)
                || ((off + len) > b.length) || ((off + len) < 0))
            throw new IndexOutOfBoundsException();
        else if (len == 0)
            return;
        int newcount = count + len;
        if (newcount > buf.length)
            buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
        System.arraycopy(b, off, buf, count, len);
        count = newcount;
    }

    public void write(byte b, int len) {
        int newcount = count + len;
        if (newcount > buf.length)
            buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
        Arrays.fill(buf, count, count + len, b);
        count = newcount;
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    }

    public void writeTo(RandomAccessFile out) throws IOException {
        out.write(buf, 0, count);
    }

    public void reset() {
        count = 0;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buf, count);
    }

    public int size() {
        return count;
    }

    @Override
    public String toString() {
        return new String(buf, 0, count);
    }

    @Override
    public void close() throws IOException {
    }
}