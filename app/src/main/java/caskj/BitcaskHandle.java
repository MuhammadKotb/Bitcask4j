package caskj;

public interface BitcaskHandle {
    void append(int key, int val);
    void merge();
    void get(int key);
}