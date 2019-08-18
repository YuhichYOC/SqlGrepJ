/*
 *
 * SqlGrepJ.java
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
package com.yoclabo.sql.scan;

import com.yoclabo.filesystem.DirectoryEntityJ;

import java.io.IOException;
import java.util.List;

public class SqlGrepJ {

    private String nameFile;
    private String patternFile;
    private NamePicker np;
    private PatternPicker pp;
    private Objects objects;

    public SqlGrepJ() {
    }

    public void setNameFile(String arg) {
        nameFile = arg;
    }

    public void setPatternFile(String arg) {
        patternFile = arg;
    }

    public List<Object> getAll() {
        return objects.getAll();
    }

    public void scan(String enc, String path) throws IOException {
        np = new NamePicker();
        np.read(nameFile);
        pp = new PatternPicker();
        pp.read(patternFile);
        objects = new Objects();
        objects.setNamePicker(np);
        objects.setPatternPicker(pp);
        DirectoryEntityJ d = new DirectoryEntityJ(enc, path);
        d.describe();
        for (DirectoryEntityJ sub : d.getSubDirectories()) {
            scanFunctions(enc, sub);
            // scanPackageBodies(enc, sub);
            scanPackages(enc, sub);
            scanProcedures(enc, sub);
            scanTriggers(enc, sub);
            scanViews(enc, sub);
        }
    }

    private void scanFunctions(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("function") || sub.getName().startsWith("Function")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.FUNCTIONS);
        }
    }

    private void scanPackageBodies(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("package") || sub.getName().startsWith("Package")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.PACKAGE_BODIES);
        }
    }

    private void scanPackages(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("package") || sub.getName().startsWith("Package")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.PACKAGES);
        }
    }

    private void scanProcedures(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("procedure") || sub.getName().startsWith("Procedure")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.PROCEDURES);
        }
    }

    private void scanTriggers(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("trigger") || sub.getName().startsWith("Trigger")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.TRIGGERS);
        }
    }

    private void scanViews(String enc, DirectoryEntityJ sub) throws IOException {
        if (sub.getName().startsWith("view") || sub.getName().startsWith("View")) {
            objects.scan(enc, sub.getPath(), Objects.SCAN_TARGET.VIEWS);
        }
    }
}
