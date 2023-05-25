package caskj;

import org.checkerframework.checker.units.qual.m;

public class MergeControl {

    private volatile boolean merging = false;
    

    public synchronized boolean isMerging() {
        return merging;
    }
    public synchronized void setMerging(boolean merging){
        this.merging = merging;
    }
}
