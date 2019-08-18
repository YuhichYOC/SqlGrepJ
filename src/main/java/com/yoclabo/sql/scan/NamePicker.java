/*
 *
 * NamePicker.java
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

import com.yoclabo.text.FileEntityJ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NamePicker {

    private static final String DEFINE_FUNCTION = "FUNCTIONS";
    private static final String DEFINE_PACKAGE_BODY = "PACKAGEBODIES";
    private static final String DEFINE_PACKAGE = "PACKAGES";
    private static final String DEFINE_PROCEDURE = "PROCEDURES";
    private static final String DEFINE_TABLE = "TABLES";
    private static final String DEFINE_TRIGGER = "TRIGGERS";
    private static final String DEFINE_VIEW = "VIEWS";

    private List<String> executables;
    private List<String> functions;
    private List<String> packageBodies;
    private List<String> packages;
    private List<String> procedures;
    private List<String> selectables;
    private List<String> tables;
    private List<String> triggers;
    private List<String> views;

    public NamePicker() {
        executables = new ArrayList<>();
        functions = new ArrayList<>();
        packageBodies = new ArrayList<>();
        packages = new ArrayList<>();
        procedures = new ArrayList<>();
        selectables = new ArrayList<>();
        tables = new ArrayList<>();
        triggers = new ArrayList<>();
        views = new ArrayList<>();
    }

    public List<String> getExecutables() {
        return executables;
    }

    public List<String> getFunctions() {
        return functions;
    }

    public List<String> getPackageBodies() {
        return packageBodies;
    }

    public List<String> getPackages() {
        return packages;
    }

    public List<String> getProcedures() {
        return procedures;
    }

    public List<String> getSelectables() {
        return selectables;
    }

    public List<String> getTables() {
        return tables;
    }

    public List<String> getTriggers() {
        return triggers;
    }

    public List<String> getViews() {
        return views;
    }

    public void read(String path) throws IOException {
        FileEntityJ f = new FileEntityJ("UTF-8", path);
        f.read();
        for (String l : f.getContent()) {
            if (!l.contains("\t")) {
                continue;
            }
            String[] split = l.split("\t", 2);
            switch (split[0]) {
                case DEFINE_FUNCTION:
                    functions.add(split[1]);
                    executables.add(split[1]);
                    break;
                case DEFINE_PACKAGE_BODY:
                    packageBodies.add(split[1]);
                    executables.add(split[1]);
                    break;
                case DEFINE_PACKAGE:
                    packages.add(split[1]);
                    executables.add(split[1]);
                    break;
                case DEFINE_PROCEDURE:
                    procedures.add(split[1]);
                    executables.add(split[1]);
                    break;
                case DEFINE_TABLE:
                    tables.add(split[1]);
                    selectables.add(split[1]);
                    break;
                case DEFINE_TRIGGER:
                    triggers.add(split[1]);
                    break;
                case DEFINE_VIEW:
                    views.add(split[1]);
                    selectables.add(split[1]);
                    break;
                default:
                    break;
            }
        }
    }

    public String pickExecutable(String arg) {
        for (String l : executables) {
            Matcher m = Pattern.compile("\\b" + l + "\\b").matcher(arg);
            if (m.find()) {
                return l;
            }
        }
        return "";
    }

    public String pickSelectable(String arg) {
        for (String l : selectables) {
            Matcher m = Pattern.compile("\\b" + l + "\\b").matcher(arg);
            if (m.find()) {
                return l;
            }
        }
        return "";
    }

    public String pickTrigger(String arg) {
        for (String l : triggers) {
            Matcher m = Pattern.compile("\\b" + l + "\\b").matcher(arg);
            if (m.find()) {
                return l;
            }
        }
        return "";
    }
}
