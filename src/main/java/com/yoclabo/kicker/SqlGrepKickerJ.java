package com.yoclabo.kicker;

import com.yoclabo.sql.scan.Object;
import com.yoclabo.sql.scan.SqlGrepJ;

import java.io.*;
import java.util.List;

public class SqlGrepKickerJ {

    public static void Main(int argc, String[] argv) {
        try {
            ArgumentPickerJ p = new ArgumentPickerJ(argv);
            p.run();
            if ("".equals(p.getNameFile())) {
                return;
            }
            if ("".equals(p.getPatternFile())) {
                return;
            }
            if ("".equals(p.getEncoding())) {
                return;
            }
            if ("".equals(p.getTargetDirectory())) {
                return;
            }
            boolean writeStdout = false;
            if ("".equals(p.getOut())) {
                writeStdout = true;
            }
            SqlGrepJ s = new SqlGrepJ();
            s.setNameFile(p.getNameFile());
            s.setPatternFile(p.getPatternFile());
            s.scan(p.getEncoding(), p.getTargetDirectory());
            List<Object> ret = s.getAll();
            if (writeStdout) {
                stdout(ret);
            }
            else {
                out(ret, p.getOut());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void stdout(List<Object> ret) {
        for (Object item : ret) {
            System.out.println(item.toString());
        }
    }

    private static void out(List<Object> ret, String path) {
        try {
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
            for (Object item : ret) {
                w.write(item.toString());
                w.newLine();
            }
            w.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
