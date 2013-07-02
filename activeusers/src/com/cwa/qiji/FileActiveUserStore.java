package com.cwa.qiji;

import java.io.*;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 用磁盘文件保存和加载ActiveUserPool。
 */
class FileActiveUserStore implements ActiveUserStore {

    private static final Logger logger = Logger.getLogger(FileActiveUserStore.class);
    
    private String fileName;

    public FileActiveUserStore(String fileName) {
        this.fileName = fileName;
    }
    
    // 把活跃玩家写入磁盘。
    @Override
    public void save(ActiveUserCache pool) {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
            save(pool, out);
        } catch (IOException e) {
            logger.error("Error while saving ActiveUserPool.", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("Can not close OutputStream.", e);
                }
            }
        }
    }
    
    private void save(ActiveUserCache pool, DataOutputStream out) throws IOException {
        for (int i = 0; i < pool.getPages().length; i++) {
            ActiveUserCachePage page = pool.getPages()[i];
            List<Integer> ids = page.getAllUsers();
            out.writeInt(ids.size());
            for (Integer id : ids) {
                out.writeInt(id);
            }
        }
    }

    // 从磁盘文件中读活跃玩家。
    @Override
    public void load(ActiveUserCache pool) {
        File dataFile = new File(fileName);
        if (! dataFile.exists()) {
            // 文件不存在
            return;
        }
        
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
            load(pool, in);
        } catch (IOException e) {
            logger.error("Error while loading ActiveUserPool.", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("Can not close InputStream.", e);
                }
            }
        }
    }
    
    private void load(ActiveUserCache pool, DataInputStream in) throws IOException {
        for (int i = 0; i < pool.getPages().length; i++) {
            ActiveUserCachePage page = pool.getPages()[i];
            int userCount = in.readInt();
            for (int j = 0; j < userCount; j++) {
                int id = in.readInt();
                page.update(id);
            }
        }
    }
    
}
