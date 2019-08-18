/*
 *
 * PatternPicker.java
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

import com.yoclabo.grep.Condition;
import com.yoclabo.text.FileEntityJ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternPicker {

    private static final String PATTERN_LABEL = "##";
    private static final String PATTERN_LABEL_FUNCTIONS = "##FUNCTIONS##";
    private static final String PATTERN_LABEL_PACKAGE_BODIES = "##PACKAGEBODIES##";
    private static final String PATTERN_LABEL_PACKAGES = "##PACKAGES##";
    private static final String PATTERN_LABEL_PROCEDURES = "##PROCEDURES##";
    private static final String PATTERN_LABEL_TRIGGERS = "##TRIGGERS##";
    private static final String PATTERN_LABEL_VIEWS = "##VIEWS##";

    private List<List<String>> functions;
    private List<List<String>> packageBodies;
    private List<List<String>> packages;
    private List<List<String>> procedures;
    private List<List<String>> triggers;
    private List<List<String>> views;

    public PatternPicker() {
        functions = new ArrayList<>();
        packageBodies = new ArrayList<>();
        packages = new ArrayList<>();
        procedures = new ArrayList<>();
        triggers = new ArrayList<>();
        views = new ArrayList<>();
    }

    public List<List<String>> getFunctions() {
        return functions;
    }

    public List<List<String>> getPackageBodies() {
        return packageBodies;
    }

    public List<List<String>> getPackages() {
        return packages;
    }

    public List<List<String>> getProcedures() {
        return procedures;
    }

    public List<List<String>> getTriggers() {
        return triggers;
    }

    public List<List<String>> getViews() {
        return views;
    }

    public void read(String path) throws IOException {
        FileEntityJ f = new FileEntityJ("UTF-8", path);
        f.read();
        Condition c_function = conditionOf(PATTERN_LABEL_FUNCTIONS, PATTERN_LABEL);
        Condition c_packageBody = conditionOf(PATTERN_LABEL_PACKAGE_BODIES, PATTERN_LABEL);
        Condition c_package = conditionOf(PATTERN_LABEL_PACKAGES, PATTERN_LABEL);
        Condition c_procedure = conditionOf(PATTERN_LABEL_PROCEDURES, PATTERN_LABEL);
        Condition c_trigger = conditionOf(PATTERN_LABEL_TRIGGERS, PATTERN_LABEL);
        Condition c_view = conditionOf(PATTERN_LABEL_VIEWS, PATTERN_LABEL);
        for (int i = 0; f.getRowCount() > i; ++i) {
            c_function.test(f.getContent().get(i), i);
            c_packageBody.test(f.getContent().get(i), i);
            c_package.test(f.getContent().get(i), i);
            c_procedure.test(f.getContent().get(i), i);
            c_trigger.test(f.getContent().get(i), i);
            c_view.test(f.getContent().get(i), i);
        }
        functions = pick(functions, c_function);
        packageBodies = pick(packageBodies, c_packageBody);
        packages = pick(packages, c_package);
        procedures = pick(procedures, c_procedure);
        triggers = pick(triggers, c_trigger);
        views = pick(views, c_view);
    }

    private Condition conditionOf(String start, String end) {
        Condition ret = new Condition();
        ret.add(start, false);
        ret.add(end, false);
        return ret;
    }

    private List<List<String>> pick(List<List<String>> vs, Condition condition) {
        if (0 < condition.getHit().size() && 2 < condition.getHit().get(0).getHit().size()) {
            for (int i = 1; condition.getHit().get(0).getHit().size() - 1 > i; ++i) {
                vs.add(List.of(condition.getHit().get(0).getHit().get(i).split("\t")));
            }
        }
        return vs;
    }

    public void fillFunctions(NamePicker np) {
        functions = fill(np, functions);
    }

    public void fillPackageBodies(NamePicker np) {
        packageBodies = fill(np, packageBodies);
    }

    public void fillPackages(NamePicker np) {
        packages = fill(np, packages);
    }

    public void fillProcedures(NamePicker np) {
        procedures = fill(np, procedures);
    }

    public void fillTriggers(NamePicker np) {
        triggers = fill(np, triggers);
    }

    public void fillViews(NamePicker np) {
        views = fill(np, views);
    }

    private List<List<String>> fill(NamePicker np, List<List<String>> target) {
        List<List<String>> replace = new ArrayList<>();
        for (List<String> subTarget : target) {
            int i = subTarget.indexOf("[:TRIGGER:]");
            int j = subTarget.indexOf("[:TABLE:]");
            int k = subTarget.indexOf("[:EXECUTABLE:]");
            List<List<String>> c = new ArrayList<>();
            c.add(new ArrayList<>());
            c = cartesian(c, (0 > i) ? new ArrayList<>() : np.getTriggers(), false);
            c = cartesian(c, (0 > j) ? new ArrayList<>() : np.getSelectables(), false);
            c = cartesian(c, (0 > k) ? new ArrayList<>() : np.getExecutables(), false);
            if (0 == c.get(0).size()) {
                continue;
            }
            int total = c.size();
            List<List<String>> ps = new ArrayList<>();
            for (int l = 0; total > l; ++l) {
                ps.add(new ArrayList<>(subTarget));
            }
            for (int m = 0; total > m; ++m) {
                if (-1 < k) {
                    ps.get(m).set(k, "\\b" + c.get(m).get((-1 < i ? 1 : 0) + (-1 < j ? 1 : 0)) + "\\b");
                }
                if (-1 < j) {
                    ps.get(m).set(j, "\\b" + c.get(m).get((-1 < i ? 1 : 0)) + "\\b");
                }
                if (-1 < i) {
                    ps.get(m).set(i, "\\b" + c.get(m).get(0) + "\\b");
                }
            }
            replace.addAll(ps);
        }
        return replace;
    }

    private List<List<String>> cartesian(List<List<String>> arg1, List<String> arg2, boolean shiftRight) {
        List<List<String>> ret = new ArrayList<>();
        if (0 == arg1.size()) {
            for (String a : arg2) {
                List<String> add = new ArrayList<>();
                add.add(a);
                ret.add(add);
            }
        }
        else if (0 == arg2.size()) {
            for (List<String> a1 : arg1) {
                List<String> add = new ArrayList<>();
                add.addAll(a1);
                ret.add(add);
            }
        }
        else {
            for (List<String> a1 : arg1) {
                for (String a2 : arg2) {
                    List<String> add = new ArrayList<>();
                    add.addAll(a1);
                    add.add(a2);
                    ret.add(add);
                }
            }
        }
        if (shiftRight) {
            for (List<String> r : ret) {
                r.add(0, "");
            }
        }
        return ret;
    }
}
