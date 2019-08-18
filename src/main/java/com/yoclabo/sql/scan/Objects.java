/*
 *
 * Objects.java
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

import com.yoclabo.grep.ContExtJ;
import com.yoclabo.grep.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Objects {

    private List<Object> objects;
    private PatternPicker pp;
    private NamePicker np;

    public Objects() {
        objects = new ArrayList<>();
    }

    public void setPatternPicker(PatternPicker arg) {
        pp = arg;
    }

    public void setNamePicker(NamePicker arg) {
        np = arg;
    }

    public List<Object> getAll() {
        return objects;
    }

    public void scan(String enc, String path, SCAN_TARGET target) throws IOException {
        ContExtJ c = init(target);
        c.setEncoding(enc);
        c.scan(path);
        List<Match> ret = c.getHit();
        for (int i = 0; ret.size() > i; ++i) {
            String currentFile = ret.get(i).getPath();
            i = iterateOneFileMatches(target, ret, currentFile, i);
        }
    }

    private ContExtJ init(SCAN_TARGET target) {
        ContExtJ c = new ContExtJ();
        switch (target) {
            case FUNCTIONS:
                pp.fillFunctions(np);
                c.init(pp.getFunctions(), true);
                break;
            case PACKAGE_BODIES:
                pp.fillPackageBodies(np);
                c.init(pp.getPackageBodies(), true);
                break;
            case PACKAGES:
                pp.fillPackages(np);
                c.init(pp.getPackages(), true);
                break;
            case PROCEDURES:
                pp.fillProcedures(np);
                c.init(pp.getProcedures(), true);
                break;
            case TRIGGERS:
                pp.fillTriggers(np);
                c.init(pp.getTriggers(), true);
                break;
            case VIEWS:
                pp.fillViews(np);
                c.init(pp.getViews(), true);
                break;
        }
        return c;
    }

    private int iterateOneFileMatches(SCAN_TARGET target, List<Match> ret, String currentFile, int loopCount) {
        Object object = new Object();
        object.setTag(currentFile);
        for (Match match : ret) {
            if (!currentFile.equals(match.getPath())) {
                continue;
            }
            addDeclararion(target, match, object);
            addSelect(match, object);
            addUpdate(match, object);
            addInsert(match, object);
            addDelete(match, object);
            addExecutable(match, object);
            ++loopCount;
        }
        objects.add(object);
        return loopCount;
    }

    private Object addDeclararion(SCAN_TARGET target, Match match, Object object) {
        if (!"DECLARATION".equals(match.getTag())) {
            return object;
        }
        if (target == SCAN_TARGET.TRIGGERS) {
            object.setName(np.pickTrigger(match.oneLine()));
            object.setWatchingTable(np.pickSelectable(match.oneLine()));
            return object;
        }
        if (target == SCAN_TARGET.VIEWS) {
            object.setName(np.pickSelectable(match.oneLine()));
            return object;
        }
        object.setName(np.pickExecutable(match.oneLine()));
        return object;
    }

    private Object addSelect(Match match, Object object) {
        if ("SELECT".equals(match.getTag())) {
            object.addSelect(np.pickSelectable(match.oneLine()));
        }
        if ("SELECT_IMMEDIATE".equals(match.getTag())) {
            object.addSelectImmediate(np.pickSelectable(match.oneLine()));
        }
        return object;
    }

    private Object addUpdate(Match match, Object object) {
        if ("UPDATE".equals(match.getTag())) {
            object.addUpdate(np.pickSelectable(match.oneLine()));
        }
        if ("UPDATE_IMMEDIATE".equals(match.getTag())) {
            object.addUpdateImmediate(np.pickSelectable(match.oneLine()));
        }
        return object;
    }

    private Object addInsert(Match match, Object object) {
        if ("INSERT".equals(match.getTag())) {
            object.addInsert(np.pickSelectable(match.oneLine()));
        }
        if ("INSERT_IMMEDIATE".equals(match.getTag())) {
            object.addInsertImmediate(np.pickSelectable(match.oneLine()));
        }
        return object;
    }

    private Object addDelete(Match match, Object object) {
        if ("DELETE".equals(match.getTag())) {
            object.addDelete(np.pickSelectable(match.oneLine()));
        }
        if ("DELETE_IMMEDIATE".equals(match.getTag())) {
            object.addDeleteImmediate(np.pickSelectable(match.oneLine()));
        }
        return object;
    }

    private Object addExecutable(Match match, Object object) {
        if ("EXECUTABLE".equals(match.getTag())) {
            object.addExecutables(np.pickExecutable(match.oneLine()));
        }
        return object;
    }

    public enum SCAN_TARGET {
        FUNCTIONS,
        PACKAGE_BODIES,
        PACKAGES,
        PROCEDURES,
        TRIGGERS,
        VIEWS,
    }
}
