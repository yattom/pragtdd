package jp.yattom.pragtdd.inventory.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class APITest {

    @Test
    public void 一連の正常系が動作する() throws Exception {
        String name = "APIテスト用商品";
        assertThat(商品が存在しない(name), is(true));

        商品を作成する(name);
        assertThat(商品が存在する(name), is(true));
        assertThat(商品の在庫(name), is(0));

        商品の在庫を追加する(name, 6);
        assertThat(商品の在庫(name), is(6));

        商品の在庫を減らす(name, 4);
        assertThat(商品の在庫(name), is(2));
    }

    private void 商品の在庫を減らす(String name, int amount) {
        // TODO Auto-generated method stub

    }

    private void 商品の在庫を追加する(String name, int amount) {
        // TODO Auto-generated method stub

    }

    private int 商品の在庫(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    private boolean 商品が存在する(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    private void 商品を作成する(String name) {
        // TODO Auto-generated method stub

    }

    private boolean 商品が存在しない(String name) {
        // TODO Auto-generated method stub
        return false;
    }
}
