package net.androidbootcamp.jayschimmoller.entomologylog;

import java.io.Serializable;
import java.util.ArrayList;

public class FileManager implements Serializable
{
    private ArrayList<Log> logs;

    public FileManager() {
        logs = new ArrayList<Log>();
    }

    public void addLog(Log l) {
        logs.add(l);
    }
    public void removeLogAt(int index) {
        logs.remove(index);
    }
    public Log getLogAt(int index) {
        return logs.get(index);
    }
    public int getIndexOfLog(Log l) {
        return logs.indexOf(l);
    }
    public ArrayList<Log> getLogs() {
        return logs;
    }
    public int size() {
        return logs.size();
    }
    public void clear() {
        logs.clear();
    }
    public boolean contains(Log log) {
        return logs.contains(log);
    }
    public String toString(Log log) {
        return logs.get(getIndexOfLog(log)).toString();
    }
    public String toString(int index) {
        return logs.get(index).toString();
    }
}
