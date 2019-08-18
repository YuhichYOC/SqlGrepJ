package com.yoclabo.kicker;

import com.yoclabo.grep.ContExtJ;

import java.io.IOException;

public class ContExtKickerJ {

    public static void Main(int argc, String[] argv) {
        try {
            ArgumentPickerJ p = new ArgumentPickerJ(argv);
            p.run();
            if ("".equals(p.getPatternFile())) {
                return;
            }
            if ("".equals(p.getTargetDirectory())) {
                return;
            }
            ContExtJ c = new ContExtJ();
            c = setDelimiter(c, p);
            c = setEncoding(c, p);
            c = init(c, p);
            write(c, p);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static ContExtJ setDelimiter(ContExtJ c, ArgumentPickerJ p) {
        if (!("".equals(p.getDelimiter()))) {
            c.setDelimiter(p.getDelimiter());
        }
        return c;
    }

    private static ContExtJ setEncoding(ContExtJ c, ArgumentPickerJ p) {
        if (!("".equals(p.getEncoding()))) {
            c.setEncoding(p.getEncoding());
        }
        return c;
    }

    private static ContExtJ init(ContExtJ c, ArgumentPickerJ p) throws IOException {
        c.init(p.getPatternFile(), true);
        return c;
    }

    private static void write(ContExtJ c, ArgumentPickerJ p) {
        Writer w = new Writer();
        w.setWriteTag(true);
        w.setWriteStdout(p.isWriteStdout());
        w.setPath(p.getOut());
        w.Run(c.getHit());
    }
}
