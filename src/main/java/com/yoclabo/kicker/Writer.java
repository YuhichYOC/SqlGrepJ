package com.yoclabo.kicker;

import com.yoclabo.grep.Match;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Writer {

    private boolean writeTag;

    private boolean writeStdout;

    private String path;

    public void setWriteTag(boolean arg) {
        writeTag = arg;
    }

    public void setWriteStdout(boolean arg) {
        writeStdout = arg;
    }

    public void setPath(String arg) {
        path = arg;
    }

    public void Run(List<Match> arg) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; arg.size() > i; ++i) {
            for (int j = 0; arg.get(i).getHit().size() > j; ++j) {
                StringBuilder add = new StringBuilder();
                if (writeTag) {
                    add.append(arg.get(i).getTag());
                    add.append("\t");
                    add.append(i);
                    add.append("\t");
                    add.append(j);
                    add.append("\t");
                }
                add.append(arg.get(i).getPath());
                add.append("\t");
                add.append((arg.get(i).getStart() + j));
                add.append("\t");
                add.append(arg.get(i).getHit().get(j));
                ret.add(add.toString());
            }
        }
        stdout(ret);
        out(ret);
    }

    private void stdout(List<String> arg) {
        if (!writeStdout) {
            return;
        }
        for (String l : arg) {
            System.out.println(l);
        }
    }

    private void out(List<String> arg) {
        if (writeStdout) {
            return;
        }
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
            for (String l : arg) {
                w.write(l);
                w.newLine();
            }
            w.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
