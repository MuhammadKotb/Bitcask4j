package caskj;

public  class Hint {
    public Hint(int fileId, int valSize, long valPos, long tstamp) {
        this.fileId = fileId;
        this.valSize = valSize;
        this.valPos = valPos;
        this.tstamp = tstamp;
    }
    public int fileId;
    public int valSize;
    public long valPos;
    public long tstamp;
    

    @Override
    public String toString() {
        return this.fileId + " " + this.valSize + " " + this.valPos + " " + this.tstamp;
    }
}  
