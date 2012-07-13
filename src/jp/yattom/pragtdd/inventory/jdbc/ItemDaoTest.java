package jp.yattom.pragtdd.inventory.jdbc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import jp.yattom.pragtdd.inventory.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemDaoTest {
	private ItemDao dao;

	@Before
	public void データベース初期化() throws Exception {
		dao = new ItemDao();
		dao.createTable();
	}

	@After
	public void データベース破棄() throws Exception {
		dao.dropTable();
	}

	@Test
	public void Read1件できる() throws Exception {
		Item item = new Item("商品A");
		dao.save(item);
		assertThat(dao.findByName("商品A"), equalTo(item));
	}

	@Test
	public void Read1件できる_見つからない場合() throws Exception {
		Item item = new Item("商品B");
		dao.save(item);
		assertThat(dao.findByName("商品A"), is((Item)null));
	}
}
