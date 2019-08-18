package com.yoclabo.sql.scan;

import org.junit.Assert;
import org.junit.Test;

public class SqlGrepJTest {

    @Test
    public void test01() {
        try {
            String enc = "UTF-8";
            String path = SqlGrepJTest.class.getClassLoader().getResource("SqlGrepJTest/Test1").getPath();
            SqlGrepJ s = new SqlGrepJ();
            s.setNameFile(path + "/setting/names.txt");
            s.setPatternFile(path + "/setting/patterns.txt");
            s.scan(enc, path + "/sql");

            Assert.assertEquals(2, s.getAll().size());

            Assert.assertEquals("Tr_AfterIns_Accounts", s.getAll().get(0).getName());
            Assert.assertEquals("Accounts", s.getAll().get(0).getWatchingTable());
            Assert.assertEquals(1, s.getAll().get(0).getSelects().size());
            Assert.assertEquals("Tags", s.getAll().get(0).getSelects().get(0));
            Assert.assertEquals(1, s.getAll().get(0).getInserts().size());
            Assert.assertEquals("Tags", s.getAll().get(0).getInserts().get(0));

            Assert.assertEquals("Tr_BeforeIns_Bugs", s.getAll().get(1).getName());
            Assert.assertEquals("Bugs", s.getAll().get(1).getWatchingTable());
            Assert.assertEquals(1, s.getAll().get(1).getSelects().size());
            Assert.assertEquals("Accounts", s.getAll().get(1).getSelects().get(0));
            Assert.assertEquals(1, s.getAll().get(1).getInserts().size());
            Assert.assertEquals("Accounts", s.getAll().get(1).getInserts().get(0));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void test02() {
        try {
            String enc = "UTF-8";
            String path = SqlGrepJTest.class.getClassLoader().getResource("SqlGrepJTest/Test2").getPath();
            SqlGrepJ s = new SqlGrepJ();
            s.setNameFile(path + "/setting/names.txt");
            s.setPatternFile(path + "/setting/patterns.txt");
            s.scan(enc, path + "/sql");

            Assert.assertEquals(10, s.getAll().size());

            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("F_Test01")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("F_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Comments"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("F_Test02")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("F_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Screenshots"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Products"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Tags"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).filter(item -> item.getUpdates().stream().anyMatch(upd -> upd.equals("Accounts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("BugStatus"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Bugs"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test02")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Comments"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test02")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("Comments"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("BugsProducts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PB_Test02")).filter(item -> item.getExecutables().stream().anyMatch(exe -> exe.equals("PB_Test01"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test01")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Comments"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test01")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("BugStatus"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("Bugs"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("Products"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Bugs"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Products"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("PR_Test02")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("BugsProducts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_AfterIns_Accounts")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_AfterIns_Accounts")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Tags"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_AfterIns_Accounts")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("Tags"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_BeforeIns_Bugs")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_BeforeIns_Bugs")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Accounts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("Tr_BeforeIns_Bugs")).filter(item -> item.getInserts().stream().anyMatch(ins -> ins.equals("Accounts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test01")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Products"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("BugsProducts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test01")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Bugs"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test02")).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Accounts"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Bugs"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Comments"))).count());
            Assert.assertEquals(1, s.getAll().stream().filter(item -> item.getName().equals("V_Test02")).filter(item -> item.getSelects().stream().anyMatch(sel -> sel.equals("Screenshots"))).count());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Assert.fail();
        }
    }
}
