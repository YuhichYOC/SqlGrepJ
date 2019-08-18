/*
 *
 * Object.java
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Object {

    private String tag;
    private String name;
    private List<String> selects;
    private List<String> selectImmediates;
    private List<String> updates;
    private List<String> updateImmediates;
    private List<String> inserts;
    private List<String> insertImmediates;
    private List<String> deletes;
    private List<String> deleteImmediates;
    private List<String> executables;
    private String watchingTable;

    public Object() {
        selects = new ArrayList<>();
        selectImmediates = new ArrayList<>();
        updates = new ArrayList<>();
        updateImmediates = new ArrayList<>();
        inserts = new ArrayList<>();
        insertImmediates = new ArrayList<>();
        deletes = new ArrayList<>();
        deleteImmediates = new ArrayList<>();
        executables = new ArrayList<>();
    }

    public void addSelect(String arg) {
        if (selects.contains(arg)) {
            return;
        }
        selects.add(arg);
        selects.sort(Comparator.naturalOrder());
    }

    public void addSelectImmediate(String arg) {
        if (selectImmediates.contains(arg)) {
            return;
        }
        selectImmediates.add(arg);
        selectImmediates.sort(Comparator.naturalOrder());
    }

    public void addUpdate(String arg) {
        if (updates.contains(arg)) {
            return;
        }
        updates.add(arg);
        updates.sort(Comparator.naturalOrder());
    }

    public void addUpdateImmediate(String arg) {
        if (updateImmediates.contains(arg)) {
            return;
        }
        updateImmediates.add(arg);
        updateImmediates.sort(Comparator.naturalOrder());
    }

    public void addInsert(String arg) {
        if (inserts.contains(arg)) {
            return;
        }
        inserts.add(arg);
        inserts.sort(Comparator.naturalOrder());
    }

    public void addInsertImmediate(String arg) {
        if (insertImmediates.contains(arg)) {
            return;
        }
        insertImmediates.add(arg);
        insertImmediates.sort(Comparator.naturalOrder());
    }

    public void addDelete(String arg) {
        if (deletes.contains(arg)) {
            return;
        }
        deletes.add(arg);
        deletes.sort(Comparator.naturalOrder());
    }

    public void addDeleteImmediate(String arg) {
        if (deleteImmediates.contains(arg)) {
            return;
        }
        deleteImmediates.add(arg);
        deleteImmediates.sort(Comparator.naturalOrder());
    }

    public void addExecutables(String arg) {
        if (executables.contains(arg)) {
            return;
        }
        executables.add(arg);
        executables.sort(Comparator.naturalOrder());
    }

    public void setTag(String arg) {
        tag = arg;
    }

    public List<String> getSelects() {
        return selects;
    }

    public List<String> getSelectImmediates() {
        return selectImmediates;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public List<String> getUpdateImmediates() {
        return updateImmediates;
    }

    public List<String> getInserts() {
        return inserts;
    }

    public List<String> getInsertImmediates() {
        return insertImmediates;
    }

    public List<String> getDeletes() {
        return deletes;
    }

    public List<String> getDeleteImmediates() {
        return deleteImmediates;
    }

    public List<String> getExecutables() {
        return executables;
    }

    public String getName() {
        return name;
    }

    public void setName(String arg) {
        name = arg;
    }

    public String getWatchingTable() {
        return watchingTable;
    }

    public void setWatchingTable(String arg) {
        watchingTable = arg;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        if (null != watchingTable && watchingTable.length() > 0) {
            ret = row(ret, "WATCHES", watchingTable);
        }
        for (String s : selects) {
            ret = row(ret, "SELECTS", s);
        }
        for (String si : selectImmediates) {
            ret = row(ret, "SELECTS_IMMEDIATE", si);
        }
        for (String u : updates) {
            ret = row(ret, "UPDATES", u);
        }
        for (String ui : updateImmediates) {
            ret = row(ret, "UPDATES_IMMEDIATE", ui);
        }
        for (String i : inserts) {
            ret = row(ret, "INSERTS", i);
        }
        for (String ii : insertImmediates) {
            ret = row(ret, "INSERTS_IMMEDIATE", ii);
        }
        for (String d : deletes) {
            ret = row(ret, "DELETES", d);
        }
        for (String di : deleteImmediates) {
            ret = row(ret, "DELETES_IMMEDIATE", di);
        }
        for (String e : executables) {
            ret = row(ret, "CALLS", e);
        }
        return ret.toString();
    }

    private StringBuilder row(StringBuilder target, String act, String item) {
        target.append(name);
        target.append("\t");
        target.append(act);
        target.append("\t");
        target.append(item);
        target.append("\r\n");
        return target;
    }
}
