package io.github.xctec.demo;

import org.rocksdb.InfoLogLevel;
import org.rocksdb.RocksDB;
import org.rocksdb.util.StdErrLogger;

public class Main2 {
    public static void main(String[] args) {
        RocksDB.loadLibrary();
        StdErrLogger i = new StdErrLogger(InfoLogLevel.ERROR_LEVEL);
    }
}
