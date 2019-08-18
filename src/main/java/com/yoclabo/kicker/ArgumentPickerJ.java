/*
 *
 * ArgumentPickerJ.java
 *
 * Copyright 2019 Yuichi Yoshii
 *     吉井雄一 @ 吉井産業  you.65535.kir@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yoclabo.kicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArgumentPickerJ {

    private String delimiter;

    private String encoding;

    private String patternFile;

    private String targetDirectory;

    private String nameFile;

    private String out;

    private boolean writeStdout;

    private List<String> args;

    public ArgumentPickerJ(String[] argv) {
        delimiter = "";
        encoding = "";
        patternFile = "";
        targetDirectory = "";
        nameFile = "";
        out = "";
        writeStdout = true;
        args = new ArrayList<>();
        for (int i = 0; argv.length > i; ++i) {
            args.add(argv[i]);
        }
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getPatternFile() {
        return patternFile;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String getOut() {
        return out;
    }

    public boolean isWriteStdout() {
        return writeStdout;
    }

    public void run() {
        pairInspection();
        pickDelimiter();
        pickEncoding();
        pickPatternFile();
        pickTargetDirectory();
        pickNameFile();
        pickOut();
    }

    private void pairInspection() {
        for (int i = 1; args.size() > i; ++i) {
            if (args.get(i - 1).startsWith("-") && args.get(i).startsWith("-")) {
                throw new IllegalArgumentException("引数 " + args.get(i - 1) + " が指定されていません");
            }
            if (args.size() - 1 == i && args.get(i).startsWith("-")) {
                throw new IllegalArgumentException("引数 " + args.get(i) + " が指定されていません");
            }
        }
    }

    private void pickDelimiter() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-d")) {
                delimiter = args.get(i + 1);
            }
        }
    }

    private void pickEncoding() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-e")) {
                encoding = args.get(i + 1);
            }
        }
    }

    private void pickPatternFile() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-p")) {
                patternFile = args.get(i + 1);
                if (!(new File(patternFile)).exists()) {
                    throw new IllegalArgumentException("パターンファイル " + patternFile + " はファイルシステム上に存在しません");
                }
            }
        }
    }

    private void pickTargetDirectory() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-t")) {
                targetDirectory = args.get(i + 1);
                if (!(new File(targetDirectory)).exists()) {
                    throw new IllegalArgumentException("ディレクトリ " + targetDirectory + " はファイルシステム上に存在しません");
                }
            }
        }
    }

    private void pickNameFile() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-n")) {
                nameFile = args.get(i + 1);
                if (!(new File(nameFile)).exists()) {
                    throw new IllegalArgumentException("名前ファイル " + nameFile + " はファイルシステム上に存在しません");
                }
            }
        }
    }

    private void pickOut() {
        for (int i = 0; args.size() > i; ++i) {
            if (args.get(i).startsWith("-o")) {
                out = args.get(i + 1);
                if ((new File(out)).exists()) {
                    throw new IllegalArgumentException("出力先ファイル " + out + " が既に存在します");
                }
                writeStdout = false;
                return;
            }
        }
        writeStdout = true;
    }
}
