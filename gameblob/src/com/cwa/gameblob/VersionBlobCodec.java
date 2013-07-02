package com.cwa.gameblob;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.util.List;

public class VersionBlobCodec<T> extends BlobCodec<T> {

    // 带版本号的Codec，版本从0开始
    private final List<? extends BlobCodec<T>> codecs;

    public VersionBlobCodec(List<? extends BlobCodec<T>> codecs) {
        this.codecs = codecs;
    }
    
    @Override
    public T read(GameInput in) {
        int version = in.getShort();
        if (version >= codecs.size()) {
            throw new RuntimeException("Unknown version " + version);
        }
        
        return codecs.get(version).read(in);
    }

    @Override
    public void write(T object, GameOutput out) {
        int currentVersion = codecs.size() - 1;
        out.putShort((short) currentVersion);
        codecs.get(currentVersion).write(object, out);
    }
    
}
